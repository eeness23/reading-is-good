package com.enes.readingisgood.exception;

public class BookNotFoundException extends BaseException {

    public BookNotFoundException(String key) {
        super(key);
    }

    public BookNotFoundException(String key, String... args) {
        super(key, args);
    }
}
