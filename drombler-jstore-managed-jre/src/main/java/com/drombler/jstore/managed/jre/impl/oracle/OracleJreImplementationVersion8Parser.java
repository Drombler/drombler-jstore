package com.drombler.jstore.managed.jre.impl.oracle;

import com.drombler.jstore.model.JStoreErrorCode;
import com.drombler.jstore.model.JStoreException;
import com.vdurmont.semver4j.Semver;

public class OracleJreImplementationVersion8Parser implements OracleJreImplementationVersionParser {

    private static final String MINOR_VERSION_DELIMITER = "u";
    private static final String BUILD_VERSION_DELIMITER = "-b";
    public static final String SEM_VER_DELIMITER = ".";

    @Override
    public Semver parseJreImplementationVersion(String jreImplementationVersion) throws JStoreException {
        String[] versionParts = jreImplementationVersion.split(MINOR_VERSION_DELIMITER, 2);
        if (versionParts.length != 2) {
            throw new JStoreException(JStoreErrorCode.JSTORE_UNSUPPORTED_JRE_VERSION_FORMAT, "Unsupported JRE version format: " + jreImplementationVersion);
        }
        String majorVersion = versionParts[0];
        String minorVersion = versionParts[1];
        String buildVersion = null;
        if (minorVersion.contains(BUILD_VERSION_DELIMITER)) {
            String[] subVersionParts = minorVersion.split(BUILD_VERSION_DELIMITER, 2);
            minorVersion = subVersionParts[0];
            buildVersion = subVersionParts[1];
        }

        try {
            Integer.parseInt(majorVersion);
            Integer.parseInt(minorVersion);
            if (buildVersion != null) {
                Integer.parseInt(buildVersion);
            }
        } catch (NumberFormatException nfe) {
            throw new JStoreException(JStoreErrorCode.JSTORE_UNSUPPORTED_JRE_VERSION_FORMAT, "Unsupported JRE version format: " + jreImplementationVersion);
        }
        StringBuilder sb = new StringBuilder(majorVersion)
                .append(SEM_VER_DELIMITER)
                .append(minorVersion)
                .append(SEM_VER_DELIMITER)
                .append("0");
        if (buildVersion != null) {
            sb.append("+")
                    .append(buildVersion);
        }
        return new Semver(sb.toString(), Semver.SemverType.LOOSE);
    }
}
