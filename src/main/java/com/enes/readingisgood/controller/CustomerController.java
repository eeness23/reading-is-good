package com.enes.readingisgood.controller;

import com.enes.readingisgood.model.request.CustomerRequest;
import com.enes.readingisgood.model.response.CustomerOrderResponse;
import com.enes.readingisgood.model.response.Response;
import com.enes.readingisgood.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController extends BaseController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<Response<Long>> saveCustomer(@RequestBody @Valid CustomerRequest customerRequest) {
        Long id = customerService.saveCustomer(customerRequest);
        return respond(id, HttpStatus.CREATED);
    }

    @GetMapping("/orders")
    public ResponseEntity<Response<Page<CustomerOrderResponse>>> getCustomerOrders(@RequestParam(defaultValue = "0") int pageNo,
                                                                                   @RequestParam(defaultValue = "10") int pageSize) {
        return respond(customerService.getCustomerOrders(pageNo, pageSize));
    }

}
