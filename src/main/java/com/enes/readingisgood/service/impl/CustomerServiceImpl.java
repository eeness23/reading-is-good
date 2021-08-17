package com.enes.readingisgood.service.impl;

import com.enes.readingisgood.entity.UserEntity;
import com.enes.readingisgood.model.mapper.OrderMapper;
import com.enes.readingisgood.model.mapper.UserMapper;
import com.enes.readingisgood.model.request.CustomerRequest;
import com.enes.readingisgood.model.response.CustomerOrderResponse;
import com.enes.readingisgood.service.CustomerService;
import com.enes.readingisgood.service.OrderService;
import com.enes.readingisgood.service.RoleService;
import com.enes.readingisgood.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final UserService userService;
    private final RoleService roleService;
    private final OrderService orderService;
    private final UserMapper userMapper;
    private final OrderMapper orderMapper;

    @Override
    public Long saveCustomer(CustomerRequest customerRequest) {
        UserEntity userEntity = userMapper.customerRequestToUserEntity(customerRequest);
        userEntity.getRoles().add(roleService.findByName("ROLE_CUSTOMER"));
        return userService.saveUser(userEntity).getId();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CustomerOrderResponse> getCustomerOrders(int pageNo, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize);
        Long userId = userService.getCurrentUserId();
        return orderService.findAllOrdersByCustomerId(userId, pageRequest)
                .map(orderMapper::entityToCustomerOrderResponse);
    }
}
