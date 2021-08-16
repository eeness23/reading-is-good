package com.enes.readingisgood.service.impl;

import com.enes.readingisgood.entity.UserEntity;
import com.enes.readingisgood.mapper.UserMapper;
import com.enes.readingisgood.model.request.CustomerRequest;
import com.enes.readingisgood.service.CustomerService;
import com.enes.readingisgood.service.RoleService;
import com.enes.readingisgood.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final UserService userService;
    private final UserMapper userMapper;
    private final RoleService roleService;

    @Override
    public Long saveCustomer(CustomerRequest customerRequest) {
        UserEntity userEntity = userMapper.customerRequestToUserEntity(customerRequest);
        userEntity.getRoles().add(roleService.findByName("ROLE_CUSTOMER"));
        return userService.saveUser(userEntity).getId();
    }
}
