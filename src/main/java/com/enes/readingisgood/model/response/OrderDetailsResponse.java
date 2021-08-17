package com.enes.readingisgood.model.response;

import com.enes.readingisgood.enums.OrderStatus;
import com.enes.readingisgood.model.Book;
import com.enes.readingisgood.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class OrderDetailsResponse {
    private Long id;
    private User customer;
    private Book book;
    private Integer quantity;
    private BigDecimal totalPrice;
    private OrderStatus orderStatus;
    private LocalDateTime createdAt;
    private Long createdBy;
    private LocalDateTime updatedAt;
    private Long updatedBy;
}
