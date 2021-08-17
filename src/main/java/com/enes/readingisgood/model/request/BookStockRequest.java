package com.enes.readingisgood.model.request;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class BookStockRequest {
    @NotNull(message = "dto.required.field")
    @Min(value = 0, message = "dto.min-count.0")
    private Integer stock;
}
