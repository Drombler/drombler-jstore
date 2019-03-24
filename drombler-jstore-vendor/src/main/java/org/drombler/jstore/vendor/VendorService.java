package org.drombler.jstore.vendor;

import org.drombler.jstore.model.RequestInfo;
import org.drombler.jstore.model.JStoreException;
import org.drombler.jstore.protocol.json.CreateVendorRequest;
import org.drombler.jstore.protocol.json.VendorDetails;
import org.drombler.jstore.protocol.json.VendorPublicInfo;

import java.util.List;

public interface VendorService {
    VendorDetails createVendor(RequestInfo requestInfo, CreateVendorRequest vendorInfo) throws JStoreException;

    VendorPublicInfo getVendorPublicInfo(String vendorId) throws JStoreException;
    List<VendorDetails> getVendorsForVendorManagement(RequestInfo requestInfo);
    void markVendorVerified(String vendorId) throws JStoreException;

    }
