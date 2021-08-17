package com.enes.readingisgood.model;

import com.enes.readingisgood.enums.Status;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class Book {
    private Long id;
    private String name;
    private String author;
    private Integer stock;
    private BigDecimal price;
    private Status status;
}
