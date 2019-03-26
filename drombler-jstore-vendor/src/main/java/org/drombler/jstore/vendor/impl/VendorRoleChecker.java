package org.drombler.jstore.vendor.impl;

import org.drombler.jstore.integration.persistence.VendorUserEntity;
import org.drombler.jstore.protocol.json.VendorRole;

import java.util.List;

public class VendorRoleChecker {
    public boolean hasVendorRole(List<VendorUserEntity> vendorUserEntities, String username, VendorRole vendorRole) {
        return vendorUserEntities.stream().anyMatch(vendorUserEntity ->
                vendorUserEntity.getUsername().equals(username) && vendorUserEntity.getRole() == vendorRole);
    }
}
