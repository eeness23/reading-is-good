package com.enes.readingisgood.service;

import com.enes.readingisgood.model.request.CustomerRequest;

public interface CustomerService {
    Long saveCustomer(CustomerRequest customerRequest);
}

