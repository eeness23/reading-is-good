package com.enes.readingisgood.service.impl;

import com.enes.readingisgood.entity.RoleEntity;
import com.enes.readingisgood.entity.UserEntity;
import com.enes.readingisgood.exception.NotFoundException;
import com.enes.readingisgood.exception.UserException;
import com.enes.readingisgood.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    void findUserEntityByUsername_success() {
        UserEntity dummyUserEntity = getDummyUserEntity();
        String username = dummyUserEntity.getUsername();
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(dummyUserEntity));
        UserEntity result = userService.findByUsername(username);
        assertEquals(username, result.getUsername());
    }

    @Test
    void findUserEntityByUsername_notFoundUser_throwNotFoundException() {
        when(userRepository.findByUsername("test")).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> userService.findByUsername("test"));
    }

    @Test
    void findUserEntityById_success() {
        UserEntity dummyUserEntity = getDummyUserEntity();
        when(userRepository.findById(1L)).thenReturn(Optional.of(dummyUserEntity));
        UserEntity result = userService.findById(1L);
        assertEquals(1L, result.getId());
    }

    @Test
    void findUserEntityById_notFoundUser_throwNotFoundException() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> userService.findById(1L));
    }

    @Test
    void saveUser_success() {
        UserEntity dummyUserEntity = getDummyUserEntity();
        when(userRepository.existsByUsernameOrEmail(dummyUserEntity.getUsername(), dummyUserEntity.getEmail()))
                .thenReturn(false);
        when(passwordEncoder.encode(dummyUserEntity.getPassword())).thenReturn("encryptedPassword");
        when(userRepository.save(dummyUserEntity)).thenReturn(dummyUserEntity);
        UserEntity result = userService.saveUser(dummyUserEntity);
        assertEquals("encryptedPassword", result.getPassword());
    }

    @Test
    void saveUser_AlreadyExistsEmailOrUsername_throwUserException() {
        UserEntity dummyUserEntity = getDummyUserEntity();
        when(userRepository.existsByUsernameOrEmail(dummyUserEntity.getUsername(), dummyUserEntity.getEmail()))
                .thenReturn(true);
        assertThrows(UserException.class, () -> userService.saveUser(dummyUserEntity));
    }

    @Test
    void getCurrentUserUsingSecurityContext_success() {
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(1L, null, new HashSet<>());
        SecurityContext securityContext = new SecurityContextImpl();
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);

        UserEntity dummyUserEntity = getDummyUserEntity();
        when(userRepository.findById(1L)).thenReturn(Optional.of(dummyUserEntity));
        UserEntity currentUser = userService.getCurrentUser();
        assertEquals(1L, currentUser.getId());
    }

    @Test
    void getCurrentUserIdFromSecurityContext_success() {
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(1L, null, new HashSet<>());
        SecurityContext securityContext = new SecurityContextImpl();
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);

        Long currentUserId = userService.getCurrentUserId();
        assertEquals(1L, currentUserId);
    }

    public UserEntity getDummyUserEntity() {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setName("ROLE_ADMIN");

        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setUsername("test-username");
        userEntity.setName("test-name");
        userEntity.setPassword("test-password");
        userEntity.setSurname("test-surname");
        userEntity.setEmail("test@test.com");
        userEntity.getRoles().add(roleEntity);
        return userEntity;
    }
}