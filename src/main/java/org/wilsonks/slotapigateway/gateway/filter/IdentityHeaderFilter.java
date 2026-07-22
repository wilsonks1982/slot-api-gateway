package org.wilsonks.slotapigateway.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.function.ServerRequest;
import org.wilsonks.slotapigateway.security.model.JwtUser;

@Slf4j
@Component
public class IdentityHeaderFilter {
    public static final String USER_ID_HEADER = "X-User-Id";
    public static final String USER_ROLE_HEADER = "X-User-Role";
    public static final String USER_TYPE_HEADER = "X-User-Type";

    public static final String MDC_USER_ID_KEY = "userId";

    public ServerRequest apply(ServerRequest request) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            return request;
        }

        if (!(authentication.getPrincipal() instanceof JwtUser(String userId, String role, String type))) {
            return request;
        }

        MDC.put(MDC_USER_ID_KEY, userId);

        return ServerRequest.from(request)
                .header(USER_ID_HEADER, userId)
                .header(USER_ROLE_HEADER, role)
                .header(USER_TYPE_HEADER, type)
                .build();
    }

}
