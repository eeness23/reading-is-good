package com.enes.readingisgood.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
//@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
@EnableJpaAuditing
public class JPAConfiguration {

//    private static Long count = 1L;
//
//    @Bean
//    public AuditorAware<Long> auditorProvider(UserService userService) {
//        return () -> Optional.of(count++);
//    }
}
