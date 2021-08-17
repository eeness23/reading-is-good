package com.enes.readingisgood.service.impl;

import com.enes.readingisgood.entity.RoleEntity;
import com.enes.readingisgood.exception.NotFoundException;
import com.enes.readingisgood.repository.RoleRepository;
import com.enes.readingisgood.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public RoleEntity findByName(String name) {
        return roleRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException("role.not-exists", name));
    }
}
