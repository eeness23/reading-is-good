package com.enes.readingisgood.exception;

public class UserNotFoundException extends BaseException {

    public UserNotFoundException(String key) {
        super(key);
    }

    public UserNotFoundException(String key, String... args) {
        super(key, args);
    }
}
