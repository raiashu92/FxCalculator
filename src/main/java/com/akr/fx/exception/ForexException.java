package com.akr.fx.exception;

public class ForexException extends RuntimeException {

    public ForexException(String message) {
        super(message);
    }

    public ForexException(String message, Throwable cause) {
        super(message, cause);
    }
}
