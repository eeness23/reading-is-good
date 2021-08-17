package com.enes.readingisgood.model.response;

import com.enes.readingisgood.enums.Status;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class BookResponse {
    private Long id;
    private String name;
    private String author;
    private Integer stock;
    private BigDecimal price;
    private Status status;
}
