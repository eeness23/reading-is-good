package com.enes.readingisgood.model.response;

import com.enes.readingisgood.enums.OrderStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderResponse {
    private Long id;
    private OrderStatus orderStatus;
}
