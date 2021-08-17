package com.enes.readingisgood.service.impl;

import com.enes.readingisgood.entity.RoleEntity;
import com.enes.readingisgood.exception.NotFoundException;
import com.enes.readingisgood.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoleServiceImplTest {

    @InjectMocks
    private RoleServiceImpl roleService;
    @Mock
    private RoleRepository roleRepository;

    @Test
    void findRoleByName_success() {
        String roleName = "ROLE_CUSTOMER";
        RoleEntity roleEntity = getDummyRoleEntity(roleName);

        when(roleRepository.findByName(roleName)).thenReturn(Optional.of(roleEntity));
        RoleEntity result = roleService.findByName(roleName);

        assertEquals(1L, result.getId());
        assertEquals(roleName, result.getName());
    }

    @Test
    void findRoleByName_notFound_throwNotFoundException() {
        String roleName = "ROLE_CUSTOMER";
        when(roleRepository.findByName(roleName)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> roleService.findByName(roleName));
    }

    public RoleEntity getDummyRoleEntity(String roleName) {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setId(1L);
        roleEntity.setName(roleName);
        return roleEntity;
    }
}