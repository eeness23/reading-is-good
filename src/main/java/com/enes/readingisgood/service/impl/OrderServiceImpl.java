package com.enes.readingisgood.service.impl;

import com.enes.readingisgood.entity.OrderEntity;
import com.enes.readingisgood.repository.OrderRepository;
import com.enes.readingisgood.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public Page<OrderEntity> findAllOrdersByCustomerId(Long customerId, Pageable pageable) {
        return orderRepository.findAllByCustomerId(customerId, pageable);
    }

}
