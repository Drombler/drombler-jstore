package com.drombler.jstore.model;

import java.util.Objects;

public class PlatformKey {
    private final String javaSpecificationVersion;
    private final String osName;
    private final String osArch;

    public PlatformKey(String javaSpecificationVersion, String osName, String osArch) {
        this.javaSpecificationVersion = javaSpecificationVersion;
        this.osName = osName;
        this.osArch = osArch;
    }

    public String getJavaSpecificationVersion() {
        return javaSpecificationVersion;
    }

    public String getOsName() {
        return osName;
    }

    public String getOsArch() {
        return osArch;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlatformKey)) return false;
        PlatformKey that = (PlatformKey) o;
        return Objects.equals(javaSpecificationVersion, that.javaSpecificationVersion) &&
                Objects.equals(osName, that.osName) &&
                Objects.equals(osArch, that.osArch);
    }

    @Override
    public int hashCode() {
        return Objects.hash(javaSpecificationVersion, osName, osArch);
    }

    @Override
    public String toString() {
        return "PlatformKey{" +
                "javaSpecificationVersion='" + javaSpecificationVersion + '\'' +
                ", osName='" + osName + '\'' +
                ", osArch='" + osArch + '\'' +
                '}';
    }


}
