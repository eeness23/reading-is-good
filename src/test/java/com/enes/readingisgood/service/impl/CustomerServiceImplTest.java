package com.enes.readingisgood.service.impl;

import com.enes.readingisgood.entity.OrderEntity;
import com.enes.readingisgood.entity.RoleEntity;
import com.enes.readingisgood.entity.UserEntity;
import com.enes.readingisgood.model.mapper.OrderMapper;
import com.enes.readingisgood.model.mapper.UserMapper;
import com.enes.readingisgood.model.request.CustomerRequest;
import com.enes.readingisgood.model.response.CustomerOrderResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Mock
    private UserServiceImpl userService;
    @Mock
    private RoleServiceImpl roleService;
    @Mock
    private OrderServiceImpl orderService;
    @Mock
    private UserMapper userMapper;
    @Mock
    private OrderMapper orderMapper;

    @Test
    void createCustomer_Success() {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setName("ROLE_CUSTOMER");

        UserEntity dummyUserEntity = getDummyUserEntity();
        when(userMapper.customerRequestToUserEntity(getDummyCustomerRequest())).thenReturn(dummyUserEntity);
        when(roleService.findByName("ROLE_CUSTOMER")).thenReturn(roleEntity);
        when(userService.saveUser(dummyUserEntity)).thenReturn(dummyUserEntity);
        Long id = customerService.saveCustomer(getDummyCustomerRequest());

        assertTrue(dummyUserEntity.getRoles().contains(roleEntity));
        assertEquals(1L, id);
    }

    @Test
    void getCustomerOrders_Success() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<OrderEntity> orderEntity = Page.empty(pageRequest);
        Page<CustomerOrderResponse> customerOrders = Page.empty(pageRequest);
        when(orderService.findAllOrdersByCustomerId(anyLong(), eq(pageRequest))).thenReturn(orderEntity);

        Page<CustomerOrderResponse> result = customerService.getCustomerOrders(0, 10);
        assertEquals(customerOrders, result);

    }

    private CustomerRequest getDummyCustomerRequest() {
        CustomerRequest customerRequest = new CustomerRequest();
        customerRequest.setEmail("test@test.com");
        customerRequest.setName("test-name");
        customerRequest.setPassword("test-password");
        customerRequest.setSurname("test-surname");
        customerRequest.setUsername("test-username");
        return customerRequest;
    }

    private UserEntity getDummyUserEntity() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setEmail("test@test.com");
        userEntity.setName("test-name");
        userEntity.setPassword("test-password");
        userEntity.setSurname("test-surname");
        userEntity.setUsername("test-username");
        return userEntity;
    }
}