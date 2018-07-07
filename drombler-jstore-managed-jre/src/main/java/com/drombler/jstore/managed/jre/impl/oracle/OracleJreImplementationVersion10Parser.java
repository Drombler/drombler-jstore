package com.drombler.jstore.managed.jre.impl.oracle;

import com.vdurmont.semver4j.Semver;

public class OracleJreImplementationVersion10Parser implements OracleJreImplementationVersionParser {
    @Override
    public Semver parseJreImplementationVersion(String jreImplementationVersion) {
        return new Semver(jreImplementationVersion);
    }
}
