package com.enes.readingisgood.security.filter;

import com.enes.readingisgood.entity.UserEntity;
import com.enes.readingisgood.exception.UnauthorizedException;
import com.enes.readingisgood.model.request.LoginRequest;
import com.enes.readingisgood.model.response.AuthenticationResponse;
import com.enes.readingisgood.model.response.ErrorResponse;
import com.enes.readingisgood.security.service.TokenService;
import com.enes.readingisgood.service.I18nService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final I18nService i18nService;
    public static final String LOGIN_URL = "/api/auth/login";
    private static final AntPathRequestMatcher LOGIN_URL_MATCHER =
            new AntPathRequestMatcher(LOGIN_URL, "POST");

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager,
                                      TokenService tokenService, I18nService i18nService) {
        super(authenticationManager);
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.i18nService = i18nService;
        setRequiresAuthenticationRequestMatcher(LOGIN_URL_MATCHER);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            LoginRequest authenticationRequest = new ObjectMapper().readValue(request.getInputStream(), LoginRequest.class);
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword());
            return authenticationManager.authenticate(authentication);
        } catch (IOException | AuthenticationException exception) {
            throwExceptionToResponse(response);
            throw new UnauthorizedException("auth.fail-login");
        }
    }

    private void throwExceptionToResponse(HttpServletResponse response) {
        List<String> localizationMessage =
                i18nService.getLocalizationMessage("auth.fail-login", LocaleContextHolder.getLocale());
        ErrorResponse<String> errorResponse =
                new ErrorResponse<>(localizationMessage.get(0), localizationMessage.get(1));
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        try {
            new ObjectMapper().writeValue(response.getOutputStream(), errorResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException {

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
