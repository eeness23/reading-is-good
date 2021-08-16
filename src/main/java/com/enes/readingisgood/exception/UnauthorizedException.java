package com.enes.readingisgood.exception;

public class UnauthorizedException extends BaseException {

    public UnauthorizedException(String key) {
        super(key);
    }

    public UnauthorizedException(String key, String... args) {
        super(key, args);
    }
}
