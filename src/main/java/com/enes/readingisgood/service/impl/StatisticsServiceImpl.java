package com.enes.readingisgood.service.impl;

import com.enes.readingisgood.entity.OrderEntity;
import com.enes.readingisgood.enums.OrderStatus;
import com.enes.readingisgood.model.response.MonthlyStatisticResponse;
import com.enes.readingisgood.service.OrderService;
import com.enes.readingisgood.service.StatisticsService;
import com.enes.readingisgood.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Month;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

    private final OrderService orderService;
    private final UserService userService;

    @Override
    public List<MonthlyStatisticResponse> getMonthlyStatistics() {
        List<MonthlyStatisticResponse> monthlyStatistics = new ArrayList<>();
        Long currentUserId = userService.getCurrentUserId();

        List<OrderEntity> purchasedOrders =
                orderService.findAllByCustomerIdAndOrderStatus(currentUserId, OrderStatus.PURCHASED);

        Map<Integer, List<OrderEntity>> monthlyOrders = purchasedOrders
                .stream()
                .collect(Collectors
                        .groupingBy(orderEntity ->
                                orderEntity.getCreatedAt().get(ChronoField.MONTH_OF_YEAR)));

        monthlyOrders.forEach((integer, orderEntities) -> {
            String monthName = Month.of(integer).getDisplayName(TextStyle.FULL_STANDALONE, Locale.ENGLISH);
            Integer totalOrderCount = orderEntities.size();
            Integer totalBookCount = 0;
            BigDecimal totalPurchasedAmount = BigDecimal.ZERO;

            for (OrderEntity orderEntity : orderEntities) {
                totalBookCount += orderEntity.getQuantity();
                totalPurchasedAmount = totalPurchasedAmount.add(orderEntity.getTotalPrice());
            }

            MonthlyStatisticResponse monthlyStatistic = MonthlyStatisticResponse.builder()
                    .monthName(monthName)
                    .totalBookCount(totalBookCount)
                    .totalPurchasedAmount(totalPurchasedAmount)
                    .totalOrderCount(totalOrderCount)
                    .build();
            monthlyStatistics.add(monthlyStatistic);

        });

        return monthlyStatistics;
    }
}
