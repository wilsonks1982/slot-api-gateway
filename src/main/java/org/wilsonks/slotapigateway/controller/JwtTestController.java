package org.wilsonks.slotapigateway.controller;

import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.wilsonks.slotapigateway.model.JwtUser;
import org.wilsonks.slotapigateway.service.JwtService;

import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("/api/jwt-test")
@AllArgsConstructor
public class JwtTestController {

    private final JwtService jwtService;

    @GetMapping
    public ResponseEntity<Optional<JwtUser>> testJwt(@RequestParam String token) {
        log.info("Testing JWT validation");
        Optional<JwtUser> optionalJwtUser=  jwtService.validateToken(token);

        if (optionalJwtUser.isPresent()) {
            JwtUser jwtUser = optionalJwtUser.get();
            log.info("JWT is valid. User: {}", jwtUser);
            return ResponseEntity.status(200).body(optionalJwtUser);
        } else {
            log.warn("JWT is invalid or expired for token: {}", token);
            return ResponseEntity.status(401).body(optionalJwtUser); // 401 Unauthorized
        }

    }
}
