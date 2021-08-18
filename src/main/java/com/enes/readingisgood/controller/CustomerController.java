package com.enes.readingisgood.controller;

import com.enes.readingisgood.model.request.CustomerRequest;
import com.enes.readingisgood.model.response.CustomerOrderResponse;
import com.enes.readingisgood.model.response.Response;
import com.enes.readingisgood.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController extends BaseController {

    private final CustomerService customerService;

    @Secured("ROLE_ADMIN")
    @PostMapping
    public ResponseEntity<Response<Long>> saveCustomer(@RequestBody @Valid CustomerRequest customerRequest) {
        Long id = customerService.saveCustomer(customerRequest);
        return respond(id, HttpStatus.CREATED);
    }

    @Secured("ROLE_CUSTOMER")
    @GetMapping("/orders")
    public ResponseEntity<Response<Page<CustomerOrderResponse>>> getCustomerOrders(@RequestParam(defaultValue = "0") int pageNo,
                                                                                   @RequestParam(defaultValue = "10") int pageSize) {
        return respond(customerService.getCustomerOrders(pageNo, pageSize));
    }

}
