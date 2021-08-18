package com.enes.readingisgood.model.response;

import com.enes.readingisgood.enums.OrderStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CustomerOrderResponse {
    private Long id;
    private LocalDateTime createdAt;
    private Long createdBy;
    private LocalDateTime updatedAt;
    private Long updatedBy;
    private BookResponse book;
    private OrderStatus orderStatus;
}
