package com.enes.readingisgood.service;

import com.enes.readingisgood.entity.UserEntity;

public interface UserService {
    UserEntity findByUsername(String username);

    UserEntity findById(Long id);

    UserEntity saveUser(UserEntity userEntity);

    UserEntity getCurrentUser();

    Long getCurrentUserId();
}
