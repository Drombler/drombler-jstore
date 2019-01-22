package org.drombler.jstore.vendor;

import org.drombler.jstore.model.VersionedPlatform;
import org.drombler.jstore.model.JStoreException;
import org.drombler.jstore.protocol.json.SelectedJRE;
import org.drombler.jstore.protocol.json.VendorInfo;

import java.util.Optional;

public interface VendorService {
    VendorInfo createVendor(VendorInfo vendorInfo) throws JStoreException;

}
