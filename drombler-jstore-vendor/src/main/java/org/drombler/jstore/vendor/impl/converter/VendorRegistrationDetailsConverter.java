package org.drombler.jstore.vendor.impl.converter;

import org.drombler.jstore.integration.persistence.VendorEntity;
import org.drombler.jstore.integration.persistence.VendorNamespaceEntity;
import org.drombler.jstore.integration.persistence.VendorRegistrationEntity;
import org.drombler.jstore.protocol.json.VendorRegistrationDetails;

import java.util.List;
import java.util.stream.Collectors;

public class VendorRegistrationDetailsConverter {

    public VendorRegistrationDetails convertVendorRegistrationDetails(VendorEntity vendorEntity, VendorRegistrationEntity registrationEntity, List<VendorNamespaceEntity> namespaceEntities) {
        VendorRegistrationDetails registrationDetails = new VendorRegistrationDetails();
        registrationDetails.setRegistrationEmailAddress(vendorEntity.getRegistrationEmailAddress());
        if (registrationEntity != null) {
            registrationDetails.setPendingRegistrationEmailAddress(registrationEntity.getRegistrationEmailAddress());
        }
        List<String> namespaces = namespaceEntities.stream()
                .filter(namespaceEntity -> namespaceEntity.getVendor().getId().equals(vendorEntity.getId())) // just double checking
                .map(VendorNamespaceEntity::getNamespace)
                .collect(Collectors.toList());
        registrationDetails.setNamespaces(namespaces);
        return registrationDetails;
    }

    public VendorRegistrationEntity convertVendorRegistrationEntity(VendorEntity vendorEntity, VendorRegistrationDetails registrationDetails, String activationCode) {
        VendorRegistrationEntity vendorRegistrationEntity = new VendorRegistrationEntity();
        vendorRegistrationEntity.setVendor(vendorEntity);
        vendorRegistrationEntity.setRegistrationEmailAddress(registrationDetails.getRegistrationEmailAddress());
        vendorRegistrationEntity.setActivationCode(activationCode);
        return vendorRegistrationEntity;
    }

    public List<VendorNamespaceEntity> convertVendorNamespaceEntities(VendorRegistrationDetails registrationDetails, VendorEntity vendorEntity) {
        return registrationDetails.getNamespaces().stream()
                .map(namespace -> convertNamespaceEntity(vendorEntity, namespace))
                .collect(Collectors.toList());
    }

    private VendorNamespaceEntity convertNamespaceEntity(VendorEntity vendorEntity, String namespace) {
        VendorNamespaceEntity namespaceEntity = new VendorNamespaceEntity();
        namespaceEntity.setVendor(vendorEntity);
        namespaceEntity.setNamespace(namespace);
        return namespaceEntity;
    }


}
