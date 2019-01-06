package org.drombler.jstore.managed.application;

import org.drombler.jstore.model.VersionedPlatform;
import org.drombler.jstore.model.JStoreException;
import org.drombler.jstore.protocol.json.SelectedJRE;
import org.drombler.jstore.protocol.json.SystemInfo;

import java.util.Optional;

public interface ApplicationManager {
    boolean supportsVendorId(String vendorId);

    Optional<VersionedPlatform> getLatestUpgradableJREImplementationVersion(SystemInfo systemInfo, SelectedJRE selectedJRE) throws JStoreException;

    Optional<String> getLatestUpgradableJreUrl(String jreImplementationId) throws JStoreException;
}
