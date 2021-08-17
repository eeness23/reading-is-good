package com.enes.readingisgood.entity;

import com.enes.readingisgood.enums.Status;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;

@Data
@Entity(name = "roles")
public class RoleEntity extends BaseEntity {

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "status", nullable = false)
    private Status status = Status.ACTIVE;

}
