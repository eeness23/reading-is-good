package com.enes.readingisgood.exception;

public class OrderException extends BaseException {
    public OrderException(String key) {
        super(key);
    }

    public OrderException(String key, String... args) {
        super(key, args);
    }
}
