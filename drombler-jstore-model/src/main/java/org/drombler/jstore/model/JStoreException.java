package org.drombler.jstore.model;

import org.drombler.jstore.protocol.v1.json.JStoreErrorCode;
import org.softsmithy.lib.util.BusinessException;

public class JStoreException extends BusinessException {

    private static final long serialVersionUID = 2423918971447441288L;

    private final JStoreErrorCode errorCode;

    public JStoreException(JStoreErrorCode errorCode, String message) {
        super(formatMessage(errorCode, message));
        this.errorCode = errorCode;
    }

    public JStoreException(JStoreErrorCode errorCode, String message, Throwable cause) {
        super(formatMessage(errorCode, message), cause);
        this.errorCode = errorCode;
    }

    private static String formatMessage(JStoreErrorCode errorCode, String message) {
        return errorCode + ": " + message;
    }

    public JStoreErrorCode getErrorCode() {
        return errorCode;
    }
}
