package org.drombler.jstore.vendor.impl.converter;

import org.drombler.jstore.integration.persistence.VendorEntity;
import org.drombler.jstore.integration.persistence.VendorUserEntity;
import org.drombler.jstore.protocol.json.VendorRole;

public class VendorUserConverter {
    public VendorUserEntity createVendorUserEntity(VendorEntity vendorEntity, String username, VendorRole role){
        VendorUserEntity vendorUserEntity = new VendorUserEntity();
        vendorUserEntity.setVendor(vendorEntity);
        vendorUserEntity.setUsername(username);
        vendorUserEntity.setRole(role);
        return vendorUserEntity;
    }
}
