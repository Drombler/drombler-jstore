package org.drombler.jstore.vendor.impl.converter;

import org.drombler.jstore.integration.persistence.VendorEntity;
import org.drombler.jstore.integration.persistence.VendorNamespaceEntity;
import org.drombler.jstore.integration.persistence.VendorRegistrationEntity;
import org.drombler.jstore.integration.persistence.VendorUserEntity;
import org.drombler.jstore.protocol.json.VendorDetails;
import org.drombler.jstore.protocol.json.VendorRole;
import org.drombler.jstore.vendor.impl.VendorRoleChecker;

import java.util.List;

public class VendorDetailsConverter {
    private final VendorPublicInfoConverter publicInfoConverter = new VendorPublicInfoConverter();
    private final VendorRegistrationDetailsConverter registrationDetailsConverter = new VendorRegistrationDetailsConverter();
    private final VendorUserDetailsConverter userDetailsConverter = new VendorUserDetailsConverter();

    public VendorDetails convertVendorDetails(VendorEntity vendorEntity, VendorRegistrationEntity vendorRegistrationEntity, List<VendorNamespaceEntity> vendorNamespaceEntities, List<VendorUserEntity> vendorUserEntities, String username) {
        VendorDetails vendorDetails = new VendorDetails();
        vendorDetails.setPublicInfo(publicInfoConverter.convertVendorPublicInfo(vendorEntity));

        VendorRoleChecker roleChecker = new VendorRoleChecker();
        if (roleChecker.hasVendorRole(vendorUserEntities, username, VendorRole.ADMIN)) {
             vendorDetails.setRegistrationDetails(registrationDetailsConverter.convertVendorRegistrationDetails(vendorEntity, vendorRegistrationEntity, vendorNamespaceEntities));
         }

        vendorDetails.setUserDetails(userDetailsConverter.convertVendorUserDetails(vendorUserEntities));
        return vendorDetails;
    }
}
