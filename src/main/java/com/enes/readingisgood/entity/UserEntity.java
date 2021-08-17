package com.enes.readingisgood.entity;

import com.enes.readingisgood.enums.Status;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class UserEntity extends BaseEntity implements UserDetails {

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "status", nullable = false)
    private Status status = Status.ACTIVE;

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<RoleEntity> roles = new ArrayList<>();

    @OneToMany(mappedBy = "customer")
    @JsonBackReference
    private Collection<OrderEntity> orders = new ArrayList<>();

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles
                .stream()
                .map(roleEntity -> new SimpleGrantedAuthority(roleEntity.getName()))
                .collect(Collectors.toList());
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return getStatus().equals(Status.ACTIVE);
    }
}
