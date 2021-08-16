package com.enes.readingisgood.controller;

import com.enes.readingisgood.model.ErrorResponse;
import com.enes.readingisgood.model.Response;
import com.enes.readingisgood.model.ResponseList;

import java.util.List;

public abstract class BaseController {

    protected <T> Response<ResponseList<T>> respond(List<T> items) {
        return new Response<>(new ResponseList<>(items));
    }

    protected <T> Response<ResponseList<T>> respond(List<T> items, int page, int size, Long totalSize) {
        return new Response<>(new ResponseList<>(items, page, size, totalSize));
    }

    protected <T> Response<T> respond(T item) {
        return new Response<>(item);
    }

    protected Response<ErrorResponse> respond(ErrorResponse errorResponse) {
        return new Response<>(errorResponse);
    }
}
