package com.metrix.contacto.common.infrastructure.exceptions;

public class NotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String errorCode;

    public NotFoundException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
