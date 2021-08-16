package com.enes.readingisgood.exception;

public class UserAlreadyExistsException extends BaseException {

    public UserAlreadyExistsException(String key) {
        super(key);
    }

    public UserAlreadyExistsException(String key, String... args) {
        super(key, args);
    }
}
