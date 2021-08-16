package com.enes.readingisgood.exception;

public class JWTException extends BaseException {

    public JWTException(String key) {
        super(key);
    }

    public JWTException(String key, String... args) {
        super(key, args);
    }
}
