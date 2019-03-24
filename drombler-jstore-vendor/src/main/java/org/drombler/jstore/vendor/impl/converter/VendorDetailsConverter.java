package org.drombler.jstore.vendor.impl.converter;

import org.drombler.jstore.integration.persistence.*;
import org.drombler.jstore.protocol.json.VendorDetails;
import org.drombler.jstore.vendor.impl.VendorRoleChecker;

import java.util.List;

public class VendorDetailsConverter {
    private final VendorPublicInfoConverter publicInfoConverter = new VendorPublicInfoConverter();
    private final VendorRegistrationDetailsConverter registrationDetailsConverter = new VendorRegistrationDetailsConverter();

    public VendorDetails convertVendorDetails(VendorEntity vendorEntity, VendorRegistrationEntity vendorRegistrationEntity, List<VendorNamespaceEntity> vendorNamespaceEntities, List<VendorUserEntity> vendorUserEntities) {
        VendorDetails vendorDetails = new VendorDetails();
        vendorDetails.setPublicInfo(publicInfoConverter.convertVendorPublicInfo(vendorEntity));

        VendorRoleChecker roleChecker = new VendorRoleChecker();
         if (roleChecker.hasVendorRole(vendorUserEntities, VendorRole.ADMIN)) {
             vendorDetails.setRegistrationDetails(registrationDetailsConverter.convertVendorRegistrationDetails(vendorEntity, vendorRegistrationEntity, vendorNamespaceEntities));
         }
        return vendorDetails;
    }
}
