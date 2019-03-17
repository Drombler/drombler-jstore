package org.drombler.jstore.integration.persistence;

import java.net.MalformedURLException;
import java.net.URL;

public class PersistenceTestHelper {
    public static VendorEntity createVendorEntity(String vendorId) throws MalformedURLException {
        VendorEntity vendorEntity = new VendorEntity();
        vendorEntity.setVendorId(vendorId);
        vendorEntity.setName(vendorId);
        vendorEntity.setHomepage(new URL("https://www." + vendorId + ".com"));
        vendorEntity.setRegistrationEmailAddress("admin@" + vendorId + ".com");
        vendorEntity.setCustomerContactEmailAddress("contact@" + vendorId + ".com");
        vendorEntity.setStatus(VendorStatus.VERIFICATION_PENDING);
        return vendorEntity;
    }
}
