package com.enes.readingisgood.model.request;

import lombok.Data;

@Data
public class OrderRequest {
    private Long bookId;
    private Integer quantity;
}
