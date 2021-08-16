package com.enes.readingisgood.mapper;

import com.enes.readingisgood.entity.UserEntity;
import com.enes.readingisgood.model.request.CustomerRequest;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {
    public UserEntity customerRequestToUserEntity(CustomerRequest customerRequest) {
        UserEntity build = UserEntity
                .builder()
                .email(customerRequest.getEmail())
                .name(customerRequest.getName())
                .username(customerRequest.getUsername())
                .password(customerRequest.getPassword())
                .surname(customerRequest.getSurname())
                .build();
        return build;

    }
}
