package com.drombler.jstore.managed.jre.impl.oracle;

import com.drombler.jstore.managed.jre.JreInfoManager;
import com.drombler.jstore.model.PlatformKey;
import com.drombler.jstore.model.VersionedPlatform;
import com.drombler.jstore.model.JStoreErrorCode;
import com.drombler.jstore.model.JStoreException;
import com.drombler.jstore.model.TransactionalService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vdurmont.semver4j.Semver;
import org.apache.commons.lang3.StringUtils;
import org.drombler.jstore.protocol.json.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.*;

@TransactionalService
public class OracleJreInfoServiceImpl implements JreInfoManager {
    private static final String ORACLE_VENDOR_ID = "oracle";

    private static final Map<String, OracleJreImplementationVersionParser> VERSION_PARSER = new HashMap<>();

    static {
        VERSION_PARSER.put("10", new OracleJreImplementationVersion10Parser());
        VERSION_PARSER.put("8", new OracleJreImplementationVersion8Parser());
    }

    private final Map<PlatformKey, VersionedPlatform> latestPlatformReleases = new HashMap<>();
    private final Map<String, LocalDate> endOfLifeDates = new HashMap<>();


    @Autowired
    private ObjectMapper objectMapper;

    private OracleJreInfo oracleJreInfo;

    @PostConstruct
    public void init() throws IOException, JStoreException {
        try (InputStream is = OracleJreInfoServiceImpl.class.getResourceAsStream("oracleJreInfo.json")) {
            oracleJreInfo = objectMapper.readValue(is, OracleJreInfo.class);
            for (JreImplementationInfo jreImplementationInfo : oracleJreInfo.getJreImplementationInfos()) {
                registerLatestRelease(jreImplementationInfo);
            }
        }
    }

    private void registerLatestRelease(JreImplementationInfo jreImplementationInfo) throws JStoreException {
        String javaSpecificationVersion = jreImplementationInfo.getJavaSpecificationVersion();
        endOfLifeDates.put(javaSpecificationVersion, jreImplementationInfo.getEndOfLifeDate());
        OracleJreImplementationVersionParser versionParser = VERSION_PARSER.get(javaSpecificationVersion);

        List<Release> releases = new ArrayList<>();
//        Semver latestImplementationVersion = versionParser.parseJreImplementationVersion(jreImplementationInfo.getPlatformSubcategories().get(0).getJreVersion());

        for (Release release : jreImplementationInfo.getReleases()) {
            Semver jreImplementationVersion = versionParser.parseJreImplementationVersion(release.getJreVersion());
            for (Platform platform : release.getPlatforms()) {
                PlatformKey platformKey = new PlatformKey(javaSpecificationVersion, platform.getOsName(), platform.getOsArch());
                if (!latestPlatformReleases.containsKey(platformKey)) {
                    latestPlatformReleases.put(platformKey, new VersionedPlatform(platformKey, jreImplementationVersion, release.getJreVersion(), release.getReleaseDate()));
                }
                VersionedPlatform versionedPlatform = latestPlatformReleases.get(platformKey);
                if (!jreImplementationVersion.isEquivalentTo(versionedPlatform.getJreImplementationVersion())
                        && jreImplementationVersion.isGreaterThan(versionedPlatform.getJreImplementationVersion())) {
                    latestPlatformReleases.put(platformKey, new VersionedPlatform(platformKey, jreImplementationVersion, release.getJreVersion(),  release.getReleaseDate()));
                }

                if (jreImplementationVersion.isEquivalentTo(versionedPlatform.getJreImplementationVersion())) {
                    versionedPlatform.getPlatformSubcategories().add(platform);
                }
            }
        }
    }

    @Override
    public boolean supportsVendorId(String vendorId) {
        return ORACLE_VENDOR_ID.equals(vendorId);
    }

    @Override
    public Optional<VersionedPlatform> getLatestUpgradableJREImplementationVersion(SystemInfo systemInfo, SelectedJRE selectedJRE) throws JStoreException {
        String javaSpecificationVersion = selectedJRE.getJreInfo().getJavaSpecificationVersion();
        if (!VERSION_PARSER.containsKey(javaSpecificationVersion)) {
            throw new JStoreException(JStoreErrorCode.JSTORE_UNSUPPORTED_JAVA_SPECIFICATION_VERSION, "Unsupported Java specification version: " + javaSpecificationVersion);
        }
        OracleJreImplementationVersionParser versionParser = VERSION_PARSER.get(javaSpecificationVersion);
        Semver jreImplementationVersion = StringUtils.isEmpty(selectedJRE.getInstalledImplementationVersion()) ? null : versionParser.parseJreImplementationVersion(selectedJRE.getInstalledImplementationVersion());
        PlatformKey platformKey = new PlatformKey(javaSpecificationVersion, systemInfo.getOsName(), systemInfo.getOsArch());

        if (latestPlatformReleases.containsKey(platformKey)) {
            VersionedPlatform versionedPlatform = latestPlatformReleases.get(platformKey);
            if (jreImplementationVersion == null ||
                    (!jreImplementationVersion.isEquivalentTo(versionedPlatform.getJreImplementationVersion())
                            && versionedPlatform.getJreImplementationVersion().isGreaterThan(jreImplementationVersion))) {
                return Optional.of(versionedPlatform);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<String> getLatestUpgradableJreUrl(SystemInfo systemInfo, SelectedJRE selectedJRE) throws JStoreException {
        Optional<Platform> foundPlatform = findLatestUpgradableJre(systemInfo, selectedJRE);
        return foundPlatform.map(Platform::getUrl);
    }

    private Optional<Platform> findLatestUpgradableJre(SystemInfo systemInfo, SelectedJRE selectedJRE) throws JStoreException {
        String javaSpecificationVersion = selectedJRE.getJreInfo().getJavaSpecificationVersion();
        if (!VERSION_PARSER.containsKey(javaSpecificationVersion)) {
            throw new JStoreException(JStoreErrorCode.JSTORE_UNSUPPORTED_JAVA_SPECIFICATION_VERSION, "Unsupported Java specification version: " + javaSpecificationVersion);
        }
        OracleJreImplementationVersionParser versionParser = VERSION_PARSER.get(javaSpecificationVersion);
        Semver jreImplementationVersion = StringUtils.isEmpty(selectedJRE.getInstalledImplementationVersion()) ? null : versionParser.parseJreImplementationVersion(selectedJRE.getInstalledImplementationVersion());
        PlatformKey platformKey = new PlatformKey(javaSpecificationVersion, systemInfo.getOsName(), systemInfo.getOsArch());

        if (latestPlatformReleases.containsKey(platformKey)) {
            VersionedPlatform versionedPlatform = latestPlatformReleases.get(platformKey);
            if (jreImplementationVersion == null ||
                    (!jreImplementationVersion.isEquivalentTo(versionedPlatform.getJreImplementationVersion())
                            && versionedPlatform.getJreImplementationVersion().isGreaterThan(jreImplementationVersion))) {
                return findPlatform(systemInfo, versionedPlatform);

            }
        }
        return Optional.empty();
    }

    private Optional<Platform> findPlatform(SystemInfo systemInfo, VersionedPlatform versionedPlatform) {
        Platform foundPlatform = null;
        for (Platform platform : versionedPlatform.getPlatformSubcategories()) {
            // non-server JREs should run on headless platforms, but non-headless platforms can require the full JRE
            if (isHeadlessSupported(systemInfo)){
                foundPlatform = findPlatformServerJreSupported(platform, foundPlatform, systemInfo);
            } else {
                foundPlatform = findPlatformServerJreNotSupported(platform, foundPlatform, systemInfo);
            }
        }
        return Optional.ofNullable(foundPlatform);
    }

    private Platform findPlatformServerJreNotSupported(Platform platform, Platform foundPlatform, SystemInfo systemInfo) {
        if (! isServerJre(platform)){
            if (foundPlatform == null || isBetterLabelMatch(platform, foundPlatform, systemInfo)){
                foundPlatform = platform;
            }
        }
        return foundPlatform;
    }

    private Platform findPlatformServerJreSupported(Platform platform, Platform foundPlatform, SystemInfo systemInfo) {
        if (isServerJre(platform)) {
            if (foundPlatform == null || ! isServerJre(foundPlatform)){
                foundPlatform = platform;
            } else {
                if (isBetterLabelMatch(platform, foundPlatform, systemInfo)){
                    foundPlatform = platform;
                }
            }
        } else {
            if (foundPlatform == null){
                foundPlatform = platform;
            } else {
                if (! isServerJre(foundPlatform) && isBetterLabelMatch(platform, foundPlatform, systemInfo)){
                    foundPlatform = platform;
                }
            }
        }
        return foundPlatform;
    }

    private boolean isHeadlessSupported(SystemInfo systemInfo) {
        return systemInfo.getHeadless() != null && systemInfo.getHeadless();
    }

    private boolean isServerJre(Platform platform) {
        return platform.getHeadless() != null && platform.getHeadless();
    }

    private boolean isBetterLabelMatch(Platform platform, Platform foundPlatform, SystemInfo systemInfo) {
        double platformRatio = calculatePlatformLabelMatchRatio(platform, systemInfo);
        double foundPlatformRatio = calculatePlatformLabelMatchRatio(foundPlatform, systemInfo);
        return platformRatio > foundPlatformRatio;
    }

    private double calculatePlatformLabelMatchRatio(Platform platform, SystemInfo systemInfo) {
        long platformMatches = systemInfo.getLabels().stream()
                .filter(platform.getLabels()::contains)
                .count();
        return platformMatches / (double) systemInfo.getLabels().size();
    }


}
