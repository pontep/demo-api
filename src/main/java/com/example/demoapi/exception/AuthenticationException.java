package com.example.demoapi.exception;

public class AuthenticationException extends RuntimeException{

    private String errorCode = "ERR40001";

    public AuthenticationException(String msg) {
        super(msg);
    }

    public AuthenticationException(String errorCode, String msg) {
        super(msg);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return this.errorCode;
    }
}
