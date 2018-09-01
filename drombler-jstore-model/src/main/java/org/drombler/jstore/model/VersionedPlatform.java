package org.drombler.jstore.model;

import org.drombler.jstore.protocol.json.Platform;

public class VersionedPlatform {

    private final VersionedPlatformCategory category;
    private final Platform platform;
    private final String jreImplementationId;
    private final String jreImplementationFileName;

    public VersionedPlatform(VersionedPlatformCategory category, Platform platform, String jreImplementationId, String jreImplementationFileName){
        this.category = category;
        this.platform = platform;
        this.jreImplementationId = jreImplementationId;
        this.jreImplementationFileName = jreImplementationFileName;
    }

    public VersionedPlatformCategory getCategory() {
        return category;
    }

    public Platform getPlatform() {
        return platform;
    }

    public String getJreImplementationId() {
        return jreImplementationId;
    }

    public String getJreImplementationFileName() {
        return jreImplementationFileName;
    }
}
