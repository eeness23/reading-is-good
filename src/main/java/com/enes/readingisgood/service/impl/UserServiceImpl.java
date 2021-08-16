package com.enes.readingisgood.service.impl;

import com.enes.readingisgood.entity.UserEntity;
import com.enes.readingisgood.exception.UserNotFoundException;
import com.enes.readingisgood.repository.UserRepository;
import com.enes.readingisgood.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("user.username.notFound", username));
    }
}
