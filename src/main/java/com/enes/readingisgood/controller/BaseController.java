package com.enes.readingisgood.controller;

import com.enes.readingisgood.model.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class BaseController {

    protected <T> ResponseEntity<Response<T>> respond(T item) {
        return ResponseEntity.ok(new Response<>(item));
    }

    protected <T> ResponseEntity<Response<T>> respond(T item, HttpStatus status) {
        return ResponseEntity.status(status).body(new Response<>(item));
    }
}
