package com.enes.readingisgood.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse<T> {
    private String errorCode;
    private T errorDescription;
}