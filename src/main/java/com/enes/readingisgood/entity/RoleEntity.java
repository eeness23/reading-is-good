package com.enes.readingisgood.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;

@Data
@Entity(name = "roles")
public class RoleEntity extends BaseEntity {

    @Column(name = "name", unique = true)
    private String name;

}
