package com.drombler.jstore.model;

import org.drombler.jstore.protocol.json.Platform;

public class VersionedPlatform {

    private final VersionedPlatformCategory category;
    private final Platform platform;

    public VersionedPlatform(VersionedPlatformCategory category, Platform platform){
        this.category = category;
        this.platform = platform;
    }

    public VersionedPlatformCategory getCategory() {
        return category;
    }

    public Platform getPlatform() {
        return platform;
    }
}
