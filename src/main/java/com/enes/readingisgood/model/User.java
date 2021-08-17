package com.enes.readingisgood.model;

import com.enes.readingisgood.entity.RoleEntity;
import com.enes.readingisgood.enums.Status;
import lombok.Data;

import java.util.Collection;

@Data
public class User {
    private Long id;
    private String email;
    private String username;
    private String name;
    private String surname;
    private Status status;
    private Collection<RoleEntity> roles;
}
