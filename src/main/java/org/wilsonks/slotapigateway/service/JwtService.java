package org.wilsonks.slotapigateway.service;

import io.jsonwebtoken.Claims;

public interface JwtService {
    Claims validateToken(String token);
}
