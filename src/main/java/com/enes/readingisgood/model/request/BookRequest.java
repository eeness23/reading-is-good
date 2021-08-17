package com.enes.readingisgood.model.request;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class BookRequest {

    @NotBlank(message = "dto.required.field")
    private String name;

    @NotBlank(message = "dto.required.field")
    private String author;

    @NotNull(message = "dto.required.field")
    @Min(value = 0, message = "dto.min-count.0")
    private Integer stock;

    @NotNull(message = "dto.required.field")
    @Min(value = 0, message = "dto.min-count.0")
    private BigDecimal price;
}
