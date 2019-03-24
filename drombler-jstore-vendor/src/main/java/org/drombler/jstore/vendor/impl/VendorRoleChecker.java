package org.drombler.jstore.vendor.impl;

import org.drombler.jstore.integration.persistence.VendorRole;
import org.drombler.jstore.integration.persistence.VendorUserEntity;

import java.util.List;

public class VendorRoleChecker {
    public boolean hasVendorRole(List<VendorUserEntity> vendorUserEntities, VendorRole vendorRole) {
        return vendorUserEntities.stream().anyMatch(vendorUserEntity -> vendorUserEntity.getRole() == vendorRole);
    }
}
