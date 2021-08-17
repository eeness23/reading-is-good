package com.enes.readingisgood.configuration;

import com.enes.readingisgood.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.enes.readingisgood.repository")
public class DBConfiguration {

    @Bean
    public AuditorAware<Long> auditorProvider(UserService userService) {
        return () -> Optional.of(userService.getCurrentUserId());
    }
}
