package com.enes.readingisgood.service.impl;

import com.enes.readingisgood.entity.BookEntity;
import com.enes.readingisgood.entity.OrderEntity;
import com.enes.readingisgood.entity.UserEntity;
import com.enes.readingisgood.enums.OrderStatus;
import com.enes.readingisgood.exception.BookException;
import com.enes.readingisgood.exception.NotFoundException;
import com.enes.readingisgood.exception.OrderException;
import com.enes.readingisgood.model.mapper.OrderMapper;
import com.enes.readingisgood.model.request.OrderRequest;
import com.enes.readingisgood.model.response.OrderDetailsResponse;
import com.enes.readingisgood.model.response.OrderResponse;
import com.enes.readingisgood.repository.OrderRepository;
import com.enes.readingisgood.service.BookService;
import com.enes.readingisgood.service.OrderService;
import com.enes.readingisgood.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final BookService bookService;
    private final OrderMapper orderMapper;
    private final UserService userService;

    @Override
    public Page<OrderEntity> findAllOrdersByCustomerId(Long customerId, Pageable pageable) {
        return orderRepository.findAllByCustomerId(customerId, pageable);
    }

    @Override
    public OrderEntity findById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("order.not-found"));
    }

    @Override
    public OrderResponse createOrder(OrderRequest orderRequest) {
        BookEntity book = bookService.findById(orderRequest.getBookId());
        if (book.getStock() < orderRequest.getQuantity()) {
            throw new BookException("book.stock-exceed", book.getName());
        } else {
            OrderEntity orderEntity = new OrderEntity();
            orderEntity.setBook(book);
            orderEntity.setQuantity(orderRequest.getQuantity());
            orderEntity.setCustomer(userService.getCurrentUser());
            book.decreaseStock(orderRequest.getQuantity());

            OrderEntity createdOrder = orderRepository.save(orderEntity);
            return orderMapper.entityToOrderResponse(createdOrder);
        }
    }

    @Override
    public OrderResponse cancelOrder(Long orderId) {
        OrderEntity orderEntity = findById(orderId);
        if (OrderStatus.CANCELLED.equals(orderEntity.getOrderStatus())) {
            throw new OrderException("order.already-cancelled");
        }

        UserEntity currentUser = userService.getCurrentUser();
        if (!orderEntity.isCancelableByUser(currentUser)) {
            throw new AccessDeniedException("auth.access-denied");
        }

        BookEntity book = orderEntity.getBook();
        book.increaseStock(orderEntity.getQuantity());
        orderEntity.setOrderStatus(OrderStatus.CANCELLED);
        OrderEntity savedOrder = saveOrder(orderEntity);
        return orderMapper.entityToOrderResponse(savedOrder);
    }

    @Override
    public OrderEntity saveOrder(OrderEntity orderEntity) {
        return orderRepository.save(orderEntity);
    }

    @Override
    public OrderDetailsResponse getOrder(Long orderId) {
        OrderEntity orderEntity = findById(orderId);
        return orderMapper.entityToOrderDetailsResponse(orderEntity);
    }

    @Override
    public Page<OrderDetailsResponse> getOrdersByDateInterval(int pageNo, int pageSize,
                                                              LocalDate startDate, LocalDate endDate) {
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize);
        return orderRepository.findAllByCreatedAtBetween(startDate.atStartOfDay(), endDate.atStartOfDay(), pageRequest)
                .map(orderMapper::entityToOrderDetailsResponse);
    }

    @Override
    public List<OrderEntity> findAllByCustomerIdAndOrderStatus(Long id, OrderStatus orderStatusType) {
        return orderRepository.findAllByCustomerIdAndOrderStatus(id, orderStatusType);
    }


}
