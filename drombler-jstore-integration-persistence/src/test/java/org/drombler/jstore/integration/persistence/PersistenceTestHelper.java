package org.drombler.jstore.integration.persistence;

public class PersistenceTestHelper {
    public static VendorEntity createVendorEntity(String vendorId){
        VendorEntity vendorEntity = new VendorEntity();
        vendorEntity.setVendorId(vendorId);
        vendorEntity.setStatus(VendorStatus.VERIFICATION_PENDING);
        return vendorEntity;
    }
}
