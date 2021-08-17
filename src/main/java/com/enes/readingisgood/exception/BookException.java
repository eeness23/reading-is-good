package com.enes.readingisgood.exception;

public class BookException extends BaseException {

    public BookException(String key) {
        super(key);
    }

    public BookException(String key, String... args) {
        super(key, args);
    }
}
