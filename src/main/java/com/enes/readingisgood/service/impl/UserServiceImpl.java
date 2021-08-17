package com.enes.readingisgood.service.impl;

import com.enes.readingisgood.entity.UserEntity;
import com.enes.readingisgood.exception.NotFoundException;
import com.enes.readingisgood.exception.UserException;
import com.enes.readingisgood.repository.UserRepository;
import com.enes.readingisgood.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("user.not-found", username));
    }

    @Override
    @Transactional(readOnly = true)
    public UserEntity findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("user.not-found", String.valueOf(id)));
    }

    @Override
    public UserEntity saveUser(UserEntity userEntity) {
        boolean existsByUsernameOrEmail =
                userRepository.existsByUsernameOrEmail(userEntity.getUsername(), userEntity.getEmail());
        if (!existsByUsernameOrEmail) {
            String password = userEntity.getPassword();
            String encryptedPassword = passwordEncoder.encode(password);
            userEntity.setPassword(encryptedPassword);
            return userRepository.save(userEntity);
        } else {
            throw new UserException("user.already-exists");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public UserEntity getCurrentUser() {
        return findById(getCurrentUserId());
    }

    @Override
    @Transactional(readOnly = true)
    public Long getCurrentUserId() {
        return Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
    }

}
