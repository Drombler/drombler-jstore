package org.drombler.jstore.vendor.impl.converter;

import org.drombler.jstore.integration.persistence.VendorEntity;
import org.drombler.jstore.protocol.json.VendorPublicInfo;

public class VendorPublicInfoConverter {

    public VendorPublicInfo convertVendorPublicInfo(VendorEntity vendorEntity){
        VendorPublicInfo vendorPublicInfo = new VendorPublicInfo();
        vendorPublicInfo.setVendorId(vendorEntity.getVendorId());
        vendorPublicInfo.setName(vendorEntity.getName());
        vendorPublicInfo.setVerified(vendorEntity.isVerified());
        vendorPublicInfo.setHomepage(vendorEntity.getHomepage());
        vendorPublicInfo.setCustomerContactEmailAddress(vendorEntity.getCustomerContactEmailAddress());
        return vendorPublicInfo;
    }

    public void configureVendorEntity(VendorEntity vendorEntity, VendorPublicInfo publicInfo) {
        // Attention: Don't set the verified property from VendorPublicInfo!!!

        vendorEntity.setVendorId(publicInfo.getVendorId());
        vendorEntity.setName(publicInfo.getName());
        vendorEntity.setHomepage(publicInfo.getHomepage());
        vendorEntity.setCustomerContactEmailAddress(publicInfo.getCustomerContactEmailAddress());
    }
}
