package org.drombler.jstore.vendor.impl;

import org.drombler.jstore.integration.persistence.VendorEntity;
import org.drombler.jstore.model.JStoreException;
import org.drombler.jstore.model.RequestInfo;
import org.drombler.jstore.protocol.json.CreateVendorRequest;
import org.drombler.jstore.protocol.json.VendorDetails;

import java.util.List;

public interface VendorPersistenceService {
    VendorDetails createVendor(RequestInfo requestInfo, CreateVendorRequest createVendorRequest, String activationCode) throws JStoreException;
    VendorEntity getActiveVendorEntity(String vendorId) throws JStoreException;
    List<VendorDetails> getVendorsForVendorManagement(String username);
    void markVendorVerified(String vendorId) throws JStoreException;
}
