package com.enes.readingisgood.security.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.enes.readingisgood.entity.RoleEntity;
import com.enes.readingisgood.entity.UserEntity;
import com.enes.readingisgood.exception.JWTException;
import com.enes.readingisgood.security.configuration.JWTConfiguration;
import com.enes.readingisgood.security.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JWTService implements TokenService {

    private final JWTConfiguration jwtConfiguration;

    @Override
    public String generateToken(UserEntity user, String issuer) {
        return JWT.create()
                .withSubject(String.valueOf(user.getId()))
                .withExpiresAt(jwtConfiguration.getExpiredTime())
                .withIssuer(issuer)
                .withIssuedAt(new Date())
                .withClaim(jwtConfiguration.getClaim().getRoleKey(),
                        user.getRoles()
                                .stream()
                                .map(RoleEntity::getName)
                                .collect(Collectors.toList()))
                .withClaim(jwtConfiguration.getClaim().getUsernameKey(), user.getUsername())
                .sign(jwtConfiguration.getAlgorithm());
    }

    @Override
    public DecodedJWT decodeJWT(String token) {
        Algorithm algorithm = jwtConfiguration.getAlgorithm();
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        try {
            return jwtVerifier.verify(token);
        } catch (TokenExpiredException e) {
            throw new JWTException("jwt.token-expired");
        } catch (SignatureVerificationException e) {
            throw new JWTException("jwt.signature-exception");
        } catch (JWTDecodeException e) {
            throw new JWTException("jwt.decode-exception");
        }
    }

    @Override
    public String getUsernameFromJWT(DecodedJWT token) {
        return token.getClaim(jwtConfiguration.getClaim().getUsernameKey()).asString();

    }

    @Override
    public Long getIdFromJWT(DecodedJWT token) {
        return Long.valueOf(token.getSubject());
    }

    @Override
    public Collection<SimpleGrantedAuthority> getRolesFromJWT(DecodedJWT token) {
        return Arrays.stream(token.getClaim(jwtConfiguration.getClaim().getRoleKey())
                .asArray(String.class))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
