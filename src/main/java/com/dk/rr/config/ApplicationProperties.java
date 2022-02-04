package com.dk.rr.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Dekai JH.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 */
@ConfigurationProperties(prefix = "rrconig", ignoreUnknownFields = false)
@Getter
@Setter
public class ApplicationProperties {
    private final ApplicationProperties.Security security = new ApplicationProperties.Security();

    @Getter
    @Setter
    public static class Security {
        private String contentSecurityPolicy = "default-src 'self'; frame-src 'self' data:; script-src 'self' 'unsafe-inline' 'unsafe-eval' https://storage.googleapis.com; style-src 'self' 'unsafe-inline'; img-src 'self' data:; font-src 'self' data:";
        private ApplicationProperties.Security.Jwt jwt = new ApplicationProperties.Security.Jwt();

        @Getter
        @Setter
        public static class Jwt {
            private String secret;
            private String base64Secret;
            private long tokenValidityInSeconds;
            private long tokenValidityInSecondsForRememberMe;
        }
    }
}
