package org.wilsonks.slotapigateway.security.model;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

public record JwtUser(

        String userId,

        String role,

        String type

) {
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(
                new SimpleGrantedAuthority("ROLE_" + role.toUpperCase())
        );
    }
}
