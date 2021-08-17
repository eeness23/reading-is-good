package com.enes.readingisgood.service;

import com.enes.readingisgood.model.response.MonthlyStatisticResponse;

import java.util.List;

public interface StatisticsService {
    List<MonthlyStatisticResponse> getMonthlyStatistics();
}
