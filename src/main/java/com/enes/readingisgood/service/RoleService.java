package com.enes.readingisgood.service;

import com.enes.readingisgood.entity.RoleEntity;

public interface RoleService {
    RoleEntity findByName(String name);
}
