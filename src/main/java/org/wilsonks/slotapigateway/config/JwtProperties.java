package org.wilsonks.slotapigateway.config;


import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "jwt")
@Getter
@Setter
@Slf4j
public class JwtProperties {
    private String secret;
    private String issuer;

    @PostConstruct
    public void validate() {
        if (secret == null || secret.length() < 32) {
            throw new IllegalArgumentException("JWT secret must be at least 32 characters long");
        }
        if (issuer == null || issuer.isEmpty()) {
            throw new IllegalArgumentException("Issuer must not be empty");
        }
        log.info("✅ JWT Properties validated successfully");
        log.info("✅ Issuer: {}", issuer);
    }
}
