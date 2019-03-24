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
import org.drombler.jstore.vendor.VendorService;
import org.drombler.jstore.vendor.impl.converter.VendorDetailsConverter;
import org.drombler.jstore.vendor.impl.converter.VendorPublicInfoConverter;
import org.drombler.jstore.vendor.impl.converter.VendorRegistrationDetailsConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.drombler.jstore.integration.nexus.client.NexusClient;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@TransactionalService
public class VendorServiceImpl implements VendorService {

    @Autowired
private VendorPersistenceService vendorPersistenceService;

    @Autowired
    private AccountActivationService accountActivationService;

    @Autowired
    private NexusClient nexusClient;

    @Override
    public VendorDetails createVendor(RequestInfo requestInfo, CreateVendorRequest createVendorRequest) throws JStoreException {
        String activationCode = accountActivationService.createActivationCode();
        VendorDetails vendorDetails = vendorPersistenceService.createVendor(requestInfo, createVendorRequest,activationCode);
       addVendorProxy(createVendorRequest);
       accountActivationService.sendActivationEmail(vendorDetails.getRegistrationDetails().getPendingRegistrationEmailAddress(), activationCode);
        return vendorDetails;
    }

    @Override
    public List<VendorDetails> getVendorsForVendorManagement(RequestInfo requestInfo) {
        Assert.hasText(requestInfo.getUsername(), "username");

        return vendorPersistenceService.getVendorsForVendorManagement(requestInfo.getUsername());
    }

    @Override
    public VendorPublicInfo getVendorPublicInfo(String vendorId) throws JStoreException {
        VendorEntity activeVendorEntity = vendorPersistenceService.getActiveVendorEntity(vendorId);
        VendorPublicInfoConverter vendorPublicInfoConverter = new VendorPublicInfoConverter();
        return vendorPublicInfoConverter.convertVendorPublicInfo(activeVendorEntity);
    }

    @Override
    public void markVendorVerified(String vendorId) throws JStoreException {
        vendorPersistenceService.markVendorVerified(vendorId);
    }

    private void addVendorProxy(CreateVendorRequest createVendorRequest) {
        nexusClient.createProxy(createVendorRequest.getPublicInfo().getVendorId());
        nexusClient.addRepoToGroup();
    }






}
