package com.enes.readingisgood.exception;

public class RoleNotFoundException extends BaseException {
    public RoleNotFoundException(String key) {
        super(key);
    }

    public RoleNotFoundException(String key, String... args) {
        super(key, args);
    }
}
