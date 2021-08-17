package com.enes.readingisgood.exception;

public class NotFoundException extends BaseException {
    public NotFoundException(String key) {
        super(key);
    }

    public NotFoundException(String key, String... args) {
        super(key, args);
    }
}
