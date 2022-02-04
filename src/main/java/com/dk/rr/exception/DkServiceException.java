package com.dk.rr.exception;

public class DkServiceException extends RuntimeException{
    private final String errorCode;

    public DkServiceException(String errorCode) {
        super();
        this.errorCode = errorCode;
    }

    public DkServiceException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public DkServiceException(String message, Throwable cause, String errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public DkServiceException(Throwable cause, String errorCode) {
        super(cause);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
