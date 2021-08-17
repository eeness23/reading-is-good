package com.enes.readingisgood.service;

import com.enes.readingisgood.model.request.CustomerRequest;
import com.enes.readingisgood.model.response.CustomerOrderResponse;
import org.springframework.data.domain.Page;

public interface CustomerService {
    Long saveCustomer(CustomerRequest customerRequest);

    Page<CustomerOrderResponse> getCustomerOrders(int pageNo, int pageSize);
}

