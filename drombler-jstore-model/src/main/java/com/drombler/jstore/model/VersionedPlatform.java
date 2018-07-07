package com.drombler.jstore.model;

import com.vdurmont.semver4j.Semver;
import org.drombler.jstore.protocol.json.Platform;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class VersionedPlatform {
    private final PlatformKey platformKey;
    private final Semver jreImplementationVersion;
    private final String jreImplementationVersionString;
    private final LocalDate releaseDate;
    private final List<Platform> platformSubcategories = new ArrayList<>();

    public VersionedPlatform(PlatformKey platformKey, Semver jreImplementationVersion, String jreImplementationVersionString, LocalDate releaseDate) {
        this.platformKey = platformKey;
        this.jreImplementationVersion = jreImplementationVersion;
        this.jreImplementationVersionString = jreImplementationVersionString;
        this.releaseDate = releaseDate;
    }

    public Semver getJreImplementationVersion() {
        return jreImplementationVersion;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public List<Platform> getPlatformSubcategories() {
        return platformSubcategories;
    }

    @Override
    public String toString() {
        return "VersionedPlatform{" +
                "jreImplementationVersion=" + jreImplementationVersion +
                ", releaseDate=" + releaseDate +
                '}';
    }

    public PlatformKey getPlatformKey() {
        return platformKey;
    }

    public String getJreImplementationVersionString() {
        return jreImplementationVersionString;
    }
}
