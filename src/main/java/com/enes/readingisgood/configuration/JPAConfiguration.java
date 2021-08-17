package com.enes.readingisgood.configuration;

import com.enes.readingisgood.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class JPAConfiguration {

    @Bean
    public AuditorAware<Long> auditorProvider(UserService userService) {
        return () -> Optional.of(userService.getCurrentUserId());
    }
}
