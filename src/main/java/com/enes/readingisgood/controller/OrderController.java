package com.enes.readingisgood.controller;

import com.enes.readingisgood.model.request.OrderRequest;
import com.enes.readingisgood.model.response.OrderDetailsResponse;
import com.enes.readingisgood.model.response.OrderResponse;
import com.enes.readingisgood.model.response.Response;
import com.enes.readingisgood.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController extends BaseController {

    private final OrderService orderService;

    @PreAuthorize("permitAll()")
    @PostMapping
    public ResponseEntity<Response<OrderResponse>> createOrder(@RequestBody OrderRequest orderRequest) {
        OrderResponse orderResponse = orderService.createOrder(orderRequest);
        return respond(orderResponse, HttpStatus.CREATED);
    }

    @PreAuthorize("permitAll()")
    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<Response<OrderResponse>> cancelOrder(@PathVariable Long orderId) {
        OrderResponse orderResponse = orderService.cancelOrder(orderId);
        return respond(orderResponse);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/{orderId}")
    public ResponseEntity<Response<OrderDetailsResponse>> getOrder(@PathVariable Long orderId) {
        OrderDetailsResponse OrderDetailsResponse = orderService.getOrder(orderId);
        return respond(OrderDetailsResponse);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping
    public ResponseEntity<Response<Page<OrderDetailsResponse>>> getOrdersByDateInterval(@RequestParam(defaultValue = "0") int pageNo,
                                                                                        @RequestParam(defaultValue = "10") int pageSize,
                                                                                        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                                                        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return respond(orderService.getOrdersByDateInterval(pageNo, pageSize, startDate, endDate));
    }
}
