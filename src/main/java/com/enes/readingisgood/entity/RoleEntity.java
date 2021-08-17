package com.enes.readingisgood.entity;

import com.enes.readingisgood.enums.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "roles")
public class RoleEntity extends BaseEntity {

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "status", nullable = false)
    private Status status = Status.ACTIVE;

}
