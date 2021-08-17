package com.enes.readingisgood.service;

import com.enes.readingisgood.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    Page<OrderEntity> findAllOrdersByCustomerId(Long customerId, Pageable pageable);
}
