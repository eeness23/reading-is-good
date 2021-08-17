package com.enes.readingisgood.model.response;

import com.enes.readingisgood.entity.BookEntity;
import com.enes.readingisgood.entity.UserEntity;
import com.enes.readingisgood.enums.OrderStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class OrderDetailsResponse {
    private Long id;
    private UserEntity customer;
    private BookEntity book;
    private Integer quantity;
    private BigDecimal totalPrice;
    private OrderStatus orderStatus;
    private LocalDateTime createdAt;
    private Long createdBy;
    private LocalDateTime updatedAt;
    private Long updatedBy;
}
