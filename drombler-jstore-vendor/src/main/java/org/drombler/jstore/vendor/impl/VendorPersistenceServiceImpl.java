package org.drombler.jstore.vendor.impl;

import org.drombler.commons.spring.jpa.stereotype.TransactionalService;
import org.drombler.jstore.integration.persistence.*;
import org.drombler.jstore.model.JStoreErrorCode;
import org.drombler.jstore.model.JStoreException;
import org.drombler.jstore.model.RequestInfo;
import org.drombler.jstore.protocol.json.CreateVendorRequest;
import org.drombler.jstore.protocol.json.VendorDetails;
import org.drombler.jstore.protocol.json.VendorPublicInfo;
import org.drombler.jstore.protocol.json.VendorRegistrationDetails;
import org.drombler.jstore.vendor.impl.converter.VendorDetailsConverter;
import org.drombler.jstore.vendor.impl.converter.VendorPublicInfoConverter;
import org.drombler.jstore.vendor.impl.converter.VendorRegistrationDetailsConverter;
import org.drombler.jstore.vendor.impl.converter.VendorUserConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@TransactionalService
public class VendorPersistenceServiceImpl implements VendorPersistenceService {
    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private VendorNamespaceRepository vendorNamespaceRepository;

    @Autowired
    private VendorRegistrationRepository vendorRegistrationRepository;

    @Autowired
    private VendorUserRepository vendorUserRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public VendorDetails createVendor(RequestInfo requestInfo, CreateVendorRequest createVendorRequest, String activationCode) throws JStoreException {
        String vendorId = createVendorRequest.getPublicInfo().getVendorId();
        VendorEntity vendorEntity = vendorRepository.findByVendorId(vendorId);
        if (vendorEntity == null){
            throw new JStoreException(JStoreErrorCode.JSTORE_VENDOR_ID_NOT_UNIQUE, "A vendor already exists with vendorId: " + vendorId);
        }
//        List<VendorNamespaceEntity> vendorNamespaceEntities = vendorNamespaceRepository.findAllByNamespaces(vendorInfo.getNamespaces());
//        if (! vendorNamespaceEntities.isEmpty()){
//// TODO
//        }
        vendorEntity = createVendorEntity(createVendorRequest);
        vendorEntity = vendorRepository.save(vendorEntity);

        VendorRegistrationEntity vendorRegistrationEntity = createVendorRegistrationEntity(vendorEntity, createVendorRequest, activationCode);
        List<VendorNamespaceEntity> vendorNamespaceEntities = createVendorNamespaceEntities(vendorEntity, createVendorRequest);
       VendorUserEntity vendorUserEntity = createAdminVendorUserEntity(vendorEntity, requestInfo);


        VendorDetailsConverter detailsConverter = new VendorDetailsConverter();
        return detailsConverter.convertVendorDetails(vendorEntity, vendorRegistrationEntity, vendorNamespaceEntities, Arrays.asList(vendorUserEntity));
    }

    @Override
    public VendorEntity getActiveVendorEntity(String vendorId) throws JStoreException {
        VendorEntity vendorEntity = getVendorEntity(vendorId);
        if (vendorEntity.getStatus() != VendorStatus.ACTIVE) {
            throw new JStoreException(JStoreErrorCode.JSTORE_VENDOR_NOT_FOUND, "No active vendor found for vendorId: " + vendorId);
        }
        return vendorEntity;
    }

    @Override
    public List<VendorDetails> getVendorsForVendorManagement(String username) {
        Assert.hasText(username, "username");

        List<VendorUserEntity> vendorUserEntities = vendorUserRepository.findByUsername(username);

        Map<Long, List<VendorUserEntity>> vendorUserEntitiesByVendor = vendorUserEntities.stream().collect(Collectors.groupingBy(vendorUserEntity -> vendorUserEntity.getVendor().getId()));
        return vendorUserEntitiesByVendor.values().stream()
                .map(this::convertVendorDetails)
                .collect(Collectors.toList());

    }

    private  VendorDetails convertVendorDetails(List<VendorUserEntity> vendorUserEntities) {
        VendorEntity vendorEntity = vendorUserEntities.get(0).getVendor();
        VendorRoleChecker roleChecker = new VendorRoleChecker();
        VendorRegistrationEntity vendorRegistrationEntity = roleChecker.hasVendorRole(vendorUserEntities, VendorRole.ADMIN)
                ? vendorRegistrationRepository.findByVendor(vendorEntity)
                : null;

        List<VendorNamespaceEntity> vendorNamespaceEntities = roleChecker.hasVendorRole(vendorUserEntities, VendorRole.ADMIN)
                ? vendorNamespaceRepository.findAllByVendor(vendorEntity)
                : null;

        VendorDetailsConverter vendorDetailsConverter = new VendorDetailsConverter();
        return vendorDetailsConverter.convertVendorDetails(vendorEntity, vendorRegistrationEntity,vendorNamespaceEntities,vendorUserEntities);
    }


    private VendorEntity getVendorEntity(String vendorId) throws JStoreException {
        VendorEntity vendorEntity = vendorRepository.findByVendorId(vendorId);
        if (vendorEntity != null) {
            throw new JStoreException(JStoreErrorCode.JSTORE_VENDOR_NOT_FOUND, "No vendor found for vendorId: " + vendorId);
        }
        return vendorEntity;
    }

    @Override
    public void markVendorVerified(String vendorId) throws JStoreException {
        VendorEntity vendorEntity = getVendorEntity(vendorId);
        vendorEntity.setVerified(true);
    }

    private VendorUserEntity createAdminVendorUserEntity(VendorEntity vendorEntity, RequestInfo requestInfo) {
        Assert.hasText(requestInfo.getUsername(), "username");

        VendorUserConverter userConverter = new VendorUserConverter();
        return userConverter.createVendorUserEntity(vendorEntity, requestInfo.getUsername(), VendorRole.ADMIN);
    }

    private VendorRegistrationEntity createVendorRegistrationEntity(VendorEntity vendorEntity, CreateVendorRequest createVendorRequest, String activationCode) {
        VendorRegistrationDetailsConverter registrationDetailsConverter = new VendorRegistrationDetailsConverter();
        return registrationDetailsConverter.convertVendorRegistrationEntity(vendorEntity, createVendorRequest.getRegistrationDetails(),activationCode);
    }

    private VendorEntity createVendorEntity(CreateVendorRequest createVendorRequest) {
        VendorEntity vendorEntity = new VendorEntity();
        VendorPublicInfo publicInfo = createVendorRequest.getPublicInfo();

        VendorPublicInfoConverter publicInfoConverter = new VendorPublicInfoConverter();
        publicInfoConverter.configureVendorEntity(vendorEntity, publicInfo);
        return vendorEntity;
    }

    private List<VendorNamespaceEntity> createVendorNamespaceEntities(VendorEntity vendorEntity, CreateVendorRequest createVendorRequest) {
        VendorRegistrationDetailsConverter registrationDetailsConverter = new VendorRegistrationDetailsConverter();
        return registrationDetailsConverter.convertVendorNamespaceEntities(createVendorRequest.getRegistrationDetails(), vendorEntity);
    }
}
