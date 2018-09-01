package com.drombler.jstore.web.controller.v1.managedcomponent.converter;

import org.drombler.jstore.protocol.json.SystemInfo;

import java.util.HashMap;
import java.util.Map;

public class SystemInfoNormalizer {

    private static final Map<String, String> OS_ARCH_SYNONYMS = new HashMap<>();
    static {
        OS_ARCH_SYNONYMS.put("amd64", "x64");
    }
    public void normalizeSystemInfo(SystemInfo systemInfo){
        systemInfo.setOsName(systemInfo.getOsName().toLowerCase());
        if (OS_ARCH_SYNONYMS.containsKey(systemInfo.getOsArch())){
            systemInfo.setOsArch(OS_ARCH_SYNONYMS.get(systemInfo.getOsArch()));
        }
    }
}
