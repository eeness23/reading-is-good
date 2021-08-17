package com.enes.readingisgood.service.impl;

import com.enes.readingisgood.entity.BookEntity;
import com.enes.readingisgood.entity.OrderEntity;
import com.enes.readingisgood.entity.RoleEntity;
import com.enes.readingisgood.entity.UserEntity;
import com.enes.readingisgood.enums.OrderStatus;
import com.enes.readingisgood.exception.BookException;
import com.enes.readingisgood.exception.OrderException;
import com.enes.readingisgood.model.mapper.OrderMapper;
import com.enes.readingisgood.model.request.OrderRequest;
import com.enes.readingisgood.model.response.OrderDetailsResponse;
import com.enes.readingisgood.model.response.OrderResponse;
import com.enes.readingisgood.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.AccessDeniedException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private BookServiceImpl bookService;
    @Mock
    private OrderMapper orderMapper;
    @Mock
    private UserServiceImpl userService;

    @Test
    void createOrder_decreaseStock_success() {
        Integer bookStockCount = 100;
        Integer quantityInOrder = 10;

        OrderRequest dummyOrderRequest = getDummyOrderRequestWithQuantity(quantityInOrder);
        BookEntity dummyBookWithEnoughStock = getDummyBookWithStock(bookStockCount);
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderStatus(OrderStatus.PURCHASED);

        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setOrderStatus(OrderStatus.PURCHASED);

        when(bookService.findById(dummyOrderRequest.getBookId()))
                .thenReturn(dummyBookWithEnoughStock);

        when(orderRepository.save(any())).thenReturn(orderEntity);

        when(orderMapper.entityToOrderResponse(orderEntity)).thenReturn(orderResponse);

        OrderResponse result = orderService.createOrder(dummyOrderRequest);

        assertEquals(OrderStatus.PURCHASED, result.getOrderStatus());
        assertEquals(90, dummyBookWithEnoughStock.getStock());
    }

    @Test
    void createOrder_WithoutEnoughStock_throwBookException() {
        Integer bookStockCount = 10;
        Integer quantityInOrder = 100;

        OrderRequest dummyOrderRequest = getDummyOrderRequestWithQuantity(quantityInOrder);
        BookEntity dummyBookWithoutEnoughStock = getDummyBookWithStock(bookStockCount);

        when(bookService.findById(dummyOrderRequest.getBookId()))
                .thenReturn(dummyBookWithoutEnoughStock);

        assertThrows(BookException.class, () -> orderService.createOrder(dummyOrderRequest));

    }

    @Test
    void cancelOrder_orderOwner_increaseStock_success() {
        Integer bookStockCount = 100;
        Integer quantityInOrder = 10;

        BookEntity dummyBookWithStock = getDummyBookWithStock(bookStockCount);
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setName("ROLE_CUSTOMER");

        UserEntity orderOwner = getDummyUserEntity(roleEntity);

        OrderEntity dummyOrderEntity =
                getDummyOrderEntity(dummyBookWithStock, OrderStatus.PURCHASED);
        dummyOrderEntity.setCustomer(orderOwner);
        dummyOrderEntity.setQuantity(quantityInOrder);

        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setOrderStatus(OrderStatus.CANCELLED);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(dummyOrderEntity));
        when(userService.getCurrentUser()).thenReturn(orderOwner);
        when(orderService.saveOrder(dummyOrderEntity)).thenReturn(dummyOrderEntity);
        when(orderMapper.entityToOrderResponse(dummyOrderEntity)).thenReturn(orderResponse);

        OrderResponse result = orderService.cancelOrder(1L);

        assertEquals(OrderStatus.CANCELLED, result.getOrderStatus());
        assertEquals(110, dummyBookWithStock.getStock());
    }

    @Test
    void cancelOrder_withAnotherUser_throwAccessDeniedException() {
        Integer bookStockCount = 100;
        Integer quantityInOrder = 10;

        BookEntity dummyBookWithStock = getDummyBookWithStock(bookStockCount);
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setName("ROLE_CUSTOMER");

        UserEntity orderOwner = getDummyUserEntity(roleEntity);

        OrderEntity dummyOrderEntity =
                getDummyOrderEntity(dummyBookWithStock, OrderStatus.PURCHASED);
        dummyOrderEntity.setCustomer(orderOwner);
        dummyOrderEntity.setQuantity(quantityInOrder);

        UserEntity anotherCustomer = getDummyUserEntity(roleEntity);
        anotherCustomer.setId(100L);

        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setOrderStatus(OrderStatus.CANCELLED);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(dummyOrderEntity));
        when(userService.getCurrentUser()).thenReturn(anotherCustomer);

        assertThrows(AccessDeniedException.class, () -> orderService.cancelOrder(1L));
    }

    @Test
    void cancelOrder_adminRole_increaseStock_success() {
        Integer bookStockCount = 100;
        Integer quantityInOrder = 10;

        BookEntity dummyBookWithStock = getDummyBookWithStock(bookStockCount);
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setName("ROLE_ADMIN");

        UserEntity orderOwner = getDummyUserEntity(roleEntity);

        OrderEntity dummyOrderEntity =
                getDummyOrderEntity(dummyBookWithStock, OrderStatus.PURCHASED);
        dummyOrderEntity.setCustomer(orderOwner);
        dummyOrderEntity.setQuantity(quantityInOrder);

        UserEntity anotherCustomer = getDummyUserEntity(roleEntity);
        anotherCustomer.setId(100L);

        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setOrderStatus(OrderStatus.CANCELLED);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(dummyOrderEntity));
        when(userService.getCurrentUser()).thenReturn(anotherCustomer);
        when(orderService.saveOrder(dummyOrderEntity)).thenReturn(dummyOrderEntity);
        when(orderMapper.entityToOrderResponse(dummyOrderEntity)).thenReturn(orderResponse);

        OrderResponse result = orderService.cancelOrder(1L);

        assertEquals(OrderStatus.CANCELLED, result.getOrderStatus());
        assertEquals(110, dummyBookWithStock.getStock());
    }


    @Test
    void createOrder_alreadyCancelled_throwOrderException() {
        OrderEntity orderEntity =
                getDummyOrderEntity(null, OrderStatus.CANCELLED);

        when(orderRepository.findById(1L))
                .thenReturn(Optional.of(orderEntity));

        assertThrows(OrderException.class, () -> orderService.cancelOrder(1L));
    }

    @Test
    void getOrderDetailsWithId_success() {
        OrderEntity orderEntity =
                getDummyOrderEntity(null, OrderStatus.PURCHASED);

        OrderDetailsResponse orderDetailsResponse = new OrderDetailsResponse();
        orderDetailsResponse.setId(1L);
        orderDetailsResponse.setOrderStatus(OrderStatus.PURCHASED);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(orderEntity));
        when(orderMapper.entityToOrderDetailsResponse(orderEntity)).thenReturn(orderDetailsResponse);
        OrderDetailsResponse result = orderService.getOrder(1L);

        assertEquals(1L, result.getId());
        assertEquals(OrderStatus.PURCHASED, result.getOrderStatus());
    }

    @Test
    void getOrdersWithDateInterval_success() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<OrderDetailsResponse> orderResponses = Page.empty(pageRequest);
        Page<OrderEntity> orderEntity = Page.empty(pageRequest);

        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plus(Period.ofDays(1));

        when(orderRepository.findAllByCreatedAtBetween(startDate.atStartOfDay(), endDate.atStartOfDay(), pageRequest))
                .thenReturn(orderEntity);

        Page<OrderDetailsResponse> result =
                orderService.getOrdersByDateInterval(0, 10, startDate, endDate);

        assertEquals(orderResponses, result);
    }

    @Test
    void findAllByCustomerIdAndOrderStatus_success() {
        Long customerId = 1L;
        OrderStatus orderStatus = OrderStatus.PURCHASED;

        BookEntity bookEntity = getDummyBookWithStock(10);
        List<OrderEntity> orderEntities = new ArrayList<>();
        OrderEntity orderEntity = getDummyOrderEntity(bookEntity, orderStatus);
        orderEntities.add(orderEntity);

        when(orderRepository.findAllByCustomerIdAndOrderStatus(customerId, orderStatus))
                .thenReturn(orderEntities);

        List<OrderEntity> result =
                orderService.findAllByCustomerIdAndOrderStatus(customerId, orderStatus);

        assertEquals(orderEntities.size(), result.size());
        assertEquals(orderEntities.get(0).getBook().getId(), result.get(0).getBook().getId());
    }

    @Test
    void findAllOrdersByCustomerId_success() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Long customerId = 1L;
        Page<OrderEntity> orderEntity = Page.empty(pageRequest);

        when(orderRepository.findAllByCustomerId(customerId, pageRequest))
                .thenReturn(orderEntity);

        Page<OrderEntity> result =
                orderService.findAllOrdersByCustomerId(customerId, pageRequest);

        assertEquals(orderEntity, result);
    }

    public OrderRequest getDummyOrderRequestWithQuantity(Integer quantity) {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setBookId(1L);
        orderRequest.setQuantity(quantity);
        return orderRequest;
    }

    public BookEntity getDummyBookWithStock(Integer stock) {
        BookEntity bookEntity = new BookEntity();
        bookEntity.setStock(stock);
        bookEntity.setId(1L);
        bookEntity.setName("test-name");
        bookEntity.setAuthor("test-author");
        bookEntity.setPrice(BigDecimal.TEN);
        return bookEntity;
    }

    public OrderEntity getDummyOrderEntity(BookEntity bookEntity, OrderStatus orderStatus) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderStatus(orderStatus);
        orderEntity.setBook(bookEntity);
        return orderEntity;
    }

    public UserEntity getDummyUserEntity(RoleEntity role) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setUsername("test-username");
        userEntity.setName("test-name");
        userEntity.setPassword("test-password");
        userEntity.setSurname("test-surname");
        userEntity.setEmail("test@test.com");
        userEntity.getRoles().add(role);
        return userEntity;
    }
}