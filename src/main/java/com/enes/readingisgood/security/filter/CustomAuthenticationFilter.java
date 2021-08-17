package com.enes.readingisgood.security.filter;

import com.enes.readingisgood.entity.UserEntity;
import com.enes.readingisgood.exception.UnauthorizedException;
import com.enes.readingisgood.model.response.AuthenticationResponse;
import com.enes.readingisgood.security.service.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    public static final String LOGIN_URL = "/auth/login";
    private static final AntPathRequestMatcher LOGIN_URL_MATCHER =
            new AntPathRequestMatcher(LOGIN_URL, "POST");

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager,
                                      TokenService tokenService) {
        super(authenticationManager);
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;

        setRequiresAuthenticationRequestMatcher(LOGIN_URL_MATCHER);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (StringUtils.isNotBlank(username) || StringUtils.isNotBlank(password)) {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(username, password);
            try {
                return authenticationManager.authenticate(authenticationToken);
            } catch (AuthenticationException exception) {
                throw new UnauthorizedException("auth.fail-login");
            }
        } else {
            throw new UnauthorizedException("auth.fail-login");
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        Object principal = authResult.getPrincipal();
        if (principal instanceof UserEntity) {
            UserEntity userEntity = (UserEntity) principal;
            String token =
                    tokenService.generateToken(userEntity, request.getRequestURL().toString());
            AuthenticationResponse authenticationResponse = new AuthenticationResponse(token);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), authenticationResponse);
        }
    }
}
