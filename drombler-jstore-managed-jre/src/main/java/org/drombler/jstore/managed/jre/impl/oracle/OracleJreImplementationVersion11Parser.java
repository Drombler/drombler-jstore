package org.drombler.jstore.managed.jre.impl.oracle;

import com.vdurmont.semver4j.Semver;

public class OracleJreImplementationVersion11Parser implements OracleJreImplementationVersionParser {
    @Override
    public Semver parseJreImplementationVersion(String jreImplementationVersion) {
        return new Semver(jreImplementationVersion, Semver.SemverType.LOOSE).toStrict();
    }
}
