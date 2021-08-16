package com.enes.readingisgood.service.impl;

import com.enes.readingisgood.entity.UserEntity;
import com.enes.readingisgood.exception.UserAlreadyExistsException;
import com.enes.readingisgood.exception.UserNotFoundException;
import com.enes.readingisgood.repository.UserRepository;
import com.enes.readingisgood.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("user.not-found", username));
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
            throw new UserAlreadyExistsException("user.already-exists");
        }
    }
}
