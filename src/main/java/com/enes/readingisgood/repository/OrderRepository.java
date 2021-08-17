package com.enes.readingisgood.repository;

import com.enes.readingisgood.entity.OrderEntity;
import com.enes.readingisgood.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    Page<OrderEntity> findAllByCustomerId(Long id, Pageable pageable);

    Page<OrderEntity> findAllByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    List<OrderEntity> findAllByCustomerIdAndOrderStatus(Long id, OrderStatus orderStatus);
}
