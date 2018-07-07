package com.drombler.jstore.managed.jre;

import org.drombler.jstore.protocol.json.SelectedJRE;
import org.drombler.jstore.protocol.json.SystemInfo;

import java.util.Optional;

public interface JreInfoManager {
    boolean supportsVendorId(String vendorId);

    Optional<String> getLatestUpgradableJREImplementationVersion(SystemInfo systemInfo, SelectedJRE selectedJRE);

    Optional<String> getLatestUpgradableJreUrl(SystemInfo systemInfo, SelectedJRE selectedJRE);
}
