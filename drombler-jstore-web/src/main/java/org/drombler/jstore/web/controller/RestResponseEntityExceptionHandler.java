package org.drombler.jstore.web.controller;

import org.drombler.jstore.model.JStoreErrorCode;
import org.drombler.jstore.model.JStoreException;
import org.drombler.jstore.protocol.json.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.EnumMap;
import java.util.Map;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Map<JStoreErrorCode, HttpStatus> CODE_STATUS_MAP = new EnumMap<>(JStoreErrorCode.class);

    static {
        CODE_STATUS_MAP.put(JStoreErrorCode.JSTORE_ILLEGAL_PROPERTY, HttpStatus.BAD_REQUEST);
        CODE_STATUS_MAP.put(JStoreErrorCode.JSTORE_UNSUPPORTED_JRE_VENDOR, HttpStatus.BAD_REQUEST);
        CODE_STATUS_MAP.put(JStoreErrorCode.JSTORE_UNSUPPORTED_JRE_VERSION_FORMAT, HttpStatus.BAD_REQUEST);
        CODE_STATUS_MAP.put(JStoreErrorCode.JSTORE_UNSUPPORTED_JAVA_SPECIFICATION_VERSION, HttpStatus.BAD_REQUEST);
        CODE_STATUS_MAP.put(JStoreErrorCode.JSTORE_JRE_IMPLEMENTATION_NOT_FOUND, HttpStatus.NOT_FOUND);
        CODE_STATUS_MAP.put(JStoreErrorCode.JSTORE_UNKNOWN, HttpStatus.INTERNAL_SERVER_ERROR);
        CODE_STATUS_MAP.put(JStoreErrorCode.JSTORE_NEXUS_CLIENT, HttpStatus.INTERNAL_SERVER_ERROR);
        CODE_STATUS_MAP.put(JStoreErrorCode.NEXUS_UNKNOWN, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(JStoreException.class)
    public ResponseEntity<ErrorResponse> handleJStoreException(JStoreException ex) {
        ErrorResponse errorResponse = createErrorResponse(ex.getErrorCode(), ex.getMessage());
        HttpStatus httpStatus = determineHttpStatus(ex.getErrorCode());
        return handleErrorResponse(errorResponse, httpStatus);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        JStoreErrorCode errorCode = JStoreErrorCode.JSTORE_ILLEGAL_PROPERTY;
        ErrorResponse errorResponse = createErrorResponse(errorCode, ex.getMessage());
        HttpStatus httpStatus = determineHttpStatus(errorCode);
        return handleErrorResponse(errorResponse, httpStatus);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex) {
        JStoreErrorCode errorCode = JStoreErrorCode.JSTORE_UNKNOWN;
        ErrorResponse errorResponse = createErrorResponse(errorCode, ex.getMessage());
        HttpStatus httpStatus = determineHttpStatus(errorCode);
        return handleErrorResponse(errorResponse, httpStatus);
    }

    private ResponseEntity<ErrorResponse> handleErrorResponse(ErrorResponse errorResponse, HttpStatus httpStatus) {
        return new ResponseEntity<>(errorResponse, httpStatus);
    }

    private ErrorResponse createErrorResponse(JStoreErrorCode errorCode, String message) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorCode(errorCode.name());
        errorResponse.setErrorMessage(message);
        return errorResponse;
    }

    private HttpStatus determineHttpStatus(JStoreErrorCode errorCode) {
        return CODE_STATUS_MAP.getOrDefault(errorCode, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
