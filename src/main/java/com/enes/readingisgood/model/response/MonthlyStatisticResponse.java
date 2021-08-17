package com.enes.readingisgood.model.response;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@Builder
@EqualsAndHashCode
public class MonthlyStatisticResponse {
    private String monthName;
    private Integer totalOrderCount;
    private Integer totalBookCount;
    private BigDecimal totalPurchasedAmount;
}
