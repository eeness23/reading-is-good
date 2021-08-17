package com.enes.readingisgood.model.response;

import com.enes.readingisgood.enums.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CustomerOrderResponse {
    private Long id;
    private String createdBy;
    private LocalDateTime createdDate;
    private String lastModifiedBy;
    private LocalDateTime lastModifiedDate;
    private BookResponse book;
    private OrderStatus orderStatus;
}
