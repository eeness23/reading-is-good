package com.enes.readingisgood.service;

import com.enes.readingisgood.entity.UserEntity;

public interface UserService {
    UserEntity findByUsername(String username);
}
