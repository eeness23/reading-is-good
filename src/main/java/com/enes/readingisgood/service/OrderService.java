package com.enes.readingisgood.service;

import com.enes.readingisgood.entity.OrderEntity;
import com.enes.readingisgood.enums.OrderStatus;
import com.enes.readingisgood.model.request.OrderRequest;
import com.enes.readingisgood.model.response.OrderDetailsResponse;
import com.enes.readingisgood.model.response.OrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {
    Page<OrderEntity> findAllOrdersByCustomerId(Long customerId, Pageable pageable);

    OrderEntity findById(Long id);

    OrderResponse createOrder(OrderRequest orderRequest);

    OrderResponse cancelOrder(Long orderId);

    OrderEntity saveOrder(OrderEntity orderEntity);

    OrderDetailsResponse getOrder(Long orderId);

    Page<OrderDetailsResponse> getOrdersByDateInterval(int pageNo, int pageSize, LocalDate startDate, LocalDate endDate);

    List<OrderEntity> findAllByCustomerIdAndOrderStatus(Long id, OrderStatus orderStatusType);
}
