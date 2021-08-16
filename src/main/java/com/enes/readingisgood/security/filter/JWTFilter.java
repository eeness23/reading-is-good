package com.enes.readingisgood.security.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.enes.readingisgood.configuration.I18nConfiguration;
import com.enes.readingisgood.exception.JWTException;
import com.enes.readingisgood.model.ErrorResponse;
import com.enes.readingisgood.security.configuration.JWTConfiguration;
import com.enes.readingisgood.security.service.impl.JWTService;
import com.enes.readingisgood.service.I18nService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Locale;
import java.util.Optional;


@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JWTConfiguration jwtConfiguration;
    private final JWTService jwtService;
    private final I18nService i18nService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response
            , FilterChain filterChain) throws ServletException, IOException {

        Optional<String> optionalToken = getTokenFromRequest(request);
        if (optionalToken.isPresent()) {
            try {
                DecodedJWT decodedJWT = jwtService.decodeJWT(optionalToken.get());
                String username = jwtService.getUsernameFromJWT(decodedJWT);
                Collection<SimpleGrantedAuthority> roles = jwtService.getRolesFromJWT(decodedJWT);

                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(username, null, roles);
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (JWTException exception) {
                ErrorResponse<String> errorResponse = generateErrorResponse(exception, request);
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                new ObjectMapper().writeValue(response.getOutputStream(), errorResponse);
            }
        }
        filterChain.doFilter(request, response);
    }

    private ErrorResponse<String> generateErrorResponse(JWTException exception, HttpServletRequest request) {
        String[] error = Optional.ofNullable(request.getHeader(I18nConfiguration.I18N_HEADER))
                .map(lang -> i18nService.getLocalizationMessage(exception.getKey(), new Locale(lang), exception.getArgs()))
                .orElseGet(() -> i18nService.getLocalizationMessage(exception.getKey(), exception.getArgs()));
        return new ErrorResponse<>(error[0], error[1]);
    }

    private Optional<String> getTokenFromRequest(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION))
                .filter(StringUtils::isNotEmpty)
                .filter(token -> token.startsWith(jwtConfiguration.getHeaderPrefix()))
                .map(token -> token.substring(jwtConfiguration.getHeaderPrefix().length() + 1));
    }
}
