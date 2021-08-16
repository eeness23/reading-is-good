package com.enes.readingisgood.exception;

import lombok.Getter;

@Getter
public abstract class BaseException extends RuntimeException {
    private final String key;
    private final String[] args;

    public BaseException(String key) {
        super(key);
        this.key = key;
        args = new String[0];
    }

    public BaseException(String key, String... args) {
        super(key);
        this.key = key;
        this.args = args;
    }
}
