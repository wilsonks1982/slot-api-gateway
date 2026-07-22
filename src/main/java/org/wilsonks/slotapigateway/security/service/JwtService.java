package org.wilsonks.slotapigateway.security.service;

import org.wilsonks.slotapigateway.security.model.JwtUser;

import java.util.Optional;

public interface JwtService {
    Optional<JwtUser> validateToken(String token);
}
