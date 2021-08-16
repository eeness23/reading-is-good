package com.enes.readingisgood.model;

import lombok.Data;

@Data
public class ErrorResponse {
    private String errorCode;
    private String errorDescription;
}