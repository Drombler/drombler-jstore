package com.drombler.jstore.managed.jre.impl.oracle;

import com.drombler.jstore.managed.jre.JreInfoManager;
import com.drombler.jstore.model.TransactionalService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.drombler.jstore.protocol.json.OracleJreInfo;
import org.drombler.jstore.protocol.json.SelectedJRE;
import org.drombler.jstore.protocol.json.SystemInfo;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

@TransactionalService
public class OracleJreInfoServiceImpl implements JreInfoManager {
    private static final String ORACLE_VENDOR_ID = "oracle";

    @Autowired
    private ObjectMapper objectMapper;

    private OracleJreInfo oracleJreInfo;

    @PostConstruct
    public void init() throws IOException {
        try (InputStream is = OracleJreInfoServiceImpl.class.getResourceAsStream("oracleJreInfo.json")) {
            oracleJreInfo = objectMapper.readValue(is, OracleJreInfo.class);
        }
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
