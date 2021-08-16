package com.enes.readingisgood.controller;

import com.enes.readingisgood.model.request.CustomerRequest;
import com.enes.readingisgood.model.response.Response;
import com.enes.readingisgood.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
