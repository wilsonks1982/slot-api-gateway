package org.wilsonks.slotapigateway.security.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.wilsonks.slotapigateway.security.config.JwtProperties;
import org.wilsonks.slotapigateway.security.model.JwtUser;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtServiceImpl implements JwtService {

    private final JwtProperties jwtProperties;

    private SecretKey secretKey;
    private JwtParser jwtParser;

    @PostConstruct
    void init() {
        secretKey = Keys.hmacShaKeyFor(
                jwtProperties.getSecret()
                        .getBytes(StandardCharsets.UTF_8));

        jwtParser = Jwts.parser()
                .verifyWith(secretKey)
                .requireIssuer(jwtProperties.getIssuer())
                .build();
    }

    @Override
    public Optional<JwtUser> validateToken(String token) {
        try {
            Claims claims =  jwtParser
                    .parseSignedClaims(token)
                    .getPayload();

            String userId = claims.getSubject();
            String type = claims.get("type", String.class);
            String role = claims.get("role", String.class);

            if (userId == null || role == null || type == null) {
                return Optional.empty();
            }
            JwtUser jwtUser =
                    new JwtUser(
                            userId,
                            type,
                            role
                    );

            return Optional.of(jwtUser);
        } catch (JwtException | IllegalArgumentException ex) {
            log.debug("JWT validation failed: {}", ex.getMessage());
            return Optional.empty();
        }
    }

}