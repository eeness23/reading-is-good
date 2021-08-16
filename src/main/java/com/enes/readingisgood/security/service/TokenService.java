package com.enes.readingisgood.security.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.enes.readingisgood.entity.UserEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;

public interface TokenService {

    String generateToken(UserEntity user, String issuer);

    DecodedJWT decodeJWT(String token);

    String getUsernameFromJWT(DecodedJWT token);

    Collection<SimpleGrantedAuthority> getRolesFromJWT(DecodedJWT token);
}
