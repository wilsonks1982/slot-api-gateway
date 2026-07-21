package org.wilsonks.slotapigateway.service;

import io.jsonwebtoken.Claims;
import org.wilsonks.slotapigateway.model.JwtUser;

import java.util.Optional;

public interface JwtService {
    Optional<JwtUser> validateToken(String token);
}
