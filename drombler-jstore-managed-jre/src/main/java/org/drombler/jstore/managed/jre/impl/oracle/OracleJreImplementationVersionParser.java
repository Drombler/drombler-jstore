package org.drombler.jstore.managed.jre.impl.oracle;

import org.drombler.jstore.model.JStoreException;
import com.vdurmont.semver4j.Semver;

public interface OracleJreImplementationVersionParser {

    Semver parseJreImplementationVersion(String jreImplementationVersion) throws JStoreException;
}
