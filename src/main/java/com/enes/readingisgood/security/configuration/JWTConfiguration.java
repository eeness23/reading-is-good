package com.enes.readingisgood.security.configuration;

import com.auth0.jwt.algorithms.Algorithm;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Data
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JWTConfiguration {
    private String key;
    private Duration duration;
    private String headerPrefix;
    private Claim claim;

    public Date getExpiredTime() {
        return Date.from(LocalDateTime.now()
                .plus(duration)
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }

    public Algorithm getAlgorithm() {
        return Algorithm.HMAC256(key.getBytes());
    }

    @Data
    public static class Claim {
        private String idKey;
        private String roleKey;
    }
}
