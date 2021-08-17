package com.enes.readingisgood.service.impl;

import com.enes.readingisgood.entity.BookEntity;
import com.enes.readingisgood.entity.OrderEntity;
import com.enes.readingisgood.entity.UserEntity;
import com.enes.readingisgood.enums.OrderStatus;
import com.enes.readingisgood.model.response.MonthlyStatisticResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StatisticsServiceImplTest {

    @InjectMocks
    private StatisticsServiceImpl statisticsService;
    @Mock
    private OrderServiceImpl orderService;
    @Mock
    private UserServiceImpl userService;

    @Test
    void getMonthlyCustomerStatistics_success() {

        Long customerId = 1L;
        List<OrderEntity> orderEntities = getDummyOrderEntities(customerId);


        when(userService.getCurrentUserId()).thenReturn(customerId);
        when(orderService.findAllByCustomerIdAndOrderStatus(customerId, OrderStatus.PURCHASED)).thenReturn(orderEntities);

        List<MonthlyStatisticResponse> expectedResult = getExpectedResult();
        List<MonthlyStatisticResponse> result = statisticsService.getMonthlyStatistics();

        assertEquals(expectedResult.size(), expectedResult.size());
        assertEquals(expectedResult, result);
    }

    private List<MonthlyStatisticResponse> getExpectedResult() {
        MonthlyStatisticResponse july = MonthlyStatisticResponse.builder()
                .monthName("July")
                .totalOrderCount(1)
                .totalBookCount(10)
                .totalPurchasedAmount(BigDecimal.valueOf(200.0))
                .build();

        MonthlyStatisticResponse august = MonthlyStatisticResponse.builder()
                .monthName("August")
                .totalOrderCount(2)
                .totalBookCount(4)
                .totalPurchasedAmount(BigDecimal.valueOf(53.0))
                .build();

        MonthlyStatisticResponse september = MonthlyStatisticResponse.builder()
                .monthName("September")
                .totalOrderCount(1)
                .totalBookCount(5)
                .totalPurchasedAmount(BigDecimal.valueOf(55.0))
                .build();
        return List.of(july, august, september);
    }

    private List<OrderEntity> getDummyOrderEntities(Long customerId) {

        LocalDate now = LocalDate.of(2021, Month.AUGUST, 18);
        LocalDate nextMonth = now.plusMonths(1);
        LocalDate beforeMonth = now.minusMonths(1);

        List<OrderEntity> orderEntities = new ArrayList<>();

        UserEntity userEntity = new UserEntity();
        userEntity.setId(customerId);

        BookEntity bookEntity = new BookEntity();
        bookEntity.setId(1L);
        bookEntity.setPrice(BigDecimal.valueOf(11.00));

        BookEntity bookEntity2 = new BookEntity();
        bookEntity2.setId(2L);
        bookEntity2.setPrice(BigDecimal.valueOf(20.00));

        OrderEntity orderEntityNow = new OrderEntity();
        orderEntityNow.setBook(bookEntity);
        orderEntityNow.setQuantity(3);
        orderEntityNow.setCreatedAt(now.atStartOfDay());
        orderEntityNow.setCustomer(userEntity);
        orderEntityNow.setOrderStatus(OrderStatus.PURCHASED);

        OrderEntity orderEntityNow2 = new OrderEntity();
        orderEntityNow2.setBook(bookEntity2);
        orderEntityNow2.setQuantity(1);
        orderEntityNow2.setCreatedAt(now.atStartOfDay());
        orderEntityNow2.setCustomer(userEntity);
        orderEntityNow2.setOrderStatus(OrderStatus.PURCHASED);

        OrderEntity orderEntityNext = new OrderEntity();
        orderEntityNext.setBook(bookEntity);
        orderEntityNext.setQuantity(5);
        orderEntityNext.setCreatedAt(nextMonth.atStartOfDay());
        orderEntityNext.setCustomer(userEntity);
        orderEntityNext.setOrderStatus(OrderStatus.PURCHASED);

        OrderEntity orderEntityBefore = new OrderEntity();
        orderEntityBefore.setBook(bookEntity2);
        orderEntityBefore.setQuantity(10);
        orderEntityBefore.setCreatedAt(beforeMonth.atStartOfDay());
        orderEntityBefore.setCustomer(userEntity);
        orderEntityBefore.setOrderStatus(OrderStatus.PURCHASED);

        OrderEntity orderEntityBefore2 = new OrderEntity();
        orderEntityBefore2.setBook(bookEntity2);
        orderEntityBefore2.setQuantity(5);
        orderEntityBefore2.setCreatedAt(beforeMonth.atStartOfDay());
        orderEntityBefore2.setCustomer(userEntity);
        orderEntityBefore2.setOrderStatus(OrderStatus.CANCELLED);

        orderEntities.add(orderEntityNow);
        orderEntities.add(orderEntityNow2);
        orderEntities.add(orderEntityNext);
        orderEntities.add(orderEntityBefore);
        return orderEntities;
    }
}