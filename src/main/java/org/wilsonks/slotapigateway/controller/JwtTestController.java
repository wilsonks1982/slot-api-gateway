package org.wilsonks.slotapigateway.controller;

import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.wilsonks.slotapigateway.service.JwtService;

@RestController
@Slf4j
@RequestMapping("/api/jwt-test")
@AllArgsConstructor
public class JwtTestController {

    private final JwtService jwtService;

    @GetMapping
    public Claims testJwt(@RequestParam String token) {
        log.info("Testing JWT validation");
            return jwtService.validateToken(token);
    }
}
