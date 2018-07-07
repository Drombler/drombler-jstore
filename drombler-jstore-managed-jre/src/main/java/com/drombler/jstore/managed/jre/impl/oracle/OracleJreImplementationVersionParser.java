package com.drombler.jstore.managed.jre.impl.oracle;

import com.drombler.jstore.model.JStoreException;
import com.vdurmont.semver4j.Semver;

public interface OracleJreImplementationVersionParser {

    Semver parseJreImplementationVersion(String jreImplementationVersion) throws JStoreException;
}
