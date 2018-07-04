package com.drombler.jstore.managed.jre.impl.oracle;

import com.drombler.jstore.managed.jre.JreInfoManager;
import org.drombler.jstore.protocol.json.SelectedJRE;
import org.drombler.jstore.protocol.json.SystemInfo;

import javax.annotation.PostConstruct;
import java.util.Optional;

//@TransactionalService
public class OracleJreInfoServiceImpl implements JreInfoManager {
    private static final String ORACLE_VENDOR_ID = "oracle";

    @PostConstruct
    public void init(){
    // read oracleJREInfo.json file
    }

    @Override
    public boolean supportsVendorId(String vendorId) {
        return ORACLE_VENDOR_ID.equals(vendorId);
    }

    @Override
    public Optional<String> getLatestUpgradableJREImplementationVersion(SystemInfo systemInfo, SelectedJRE selectedJRE) {
        return Optional.empty();
    }
}
