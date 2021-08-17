package com.enes.readingisgood.model.response;

import com.enes.readingisgood.enums.Status;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BookResponse {
    private Long id;
    private String name;
    private String author;
    private Integer stock;
    private BigDecimal price;
    private Status status;
}
