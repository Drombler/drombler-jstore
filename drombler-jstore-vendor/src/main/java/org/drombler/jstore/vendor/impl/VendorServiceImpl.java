package org.drombler.jstore.vendor.impl;

import org.drombler.jstore.model.JStoreException;
import org.drombler.jstore.model.TransactionalService;
import org.drombler.jstore.protocol.json.VendorInfo;
import org.drombler.jstore.vendor.VendorService;

@TransactionalService
public class VendorServiceImpl implements VendorService {
    @Override
    public void createVendor(VendorInfo vendorInfo) throws JStoreException {
        String vendorId = vendorInfo.getVendorId();
    }
}
