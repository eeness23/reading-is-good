package com.enes.readingisgood.exception;

public class UserException extends BaseException {

    public UserException(String key) {
        super(key);
    }

    public UserException(String key, String... args) {
        super(key, args);
    }
}
