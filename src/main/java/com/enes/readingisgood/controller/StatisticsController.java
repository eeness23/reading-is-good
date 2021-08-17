package com.enes.readingisgood.controller;

import com.enes.readingisgood.model.response.MonthlyStatisticResponse;
import com.enes.readingisgood.model.response.Response;
import com.enes.readingisgood.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/statistics")
@RequiredArgsConstructor
public class StatisticsController extends BaseController {
    private final StatisticsService statisticsService;

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/monthly")
    public ResponseEntity<Response<List<MonthlyStatisticResponse>>> getMonthlyStatistics() {
        return respond(statisticsService.getMonthlyStatistics());
    }
}
