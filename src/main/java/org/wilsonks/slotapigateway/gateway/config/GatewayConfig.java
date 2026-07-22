package org.wilsonks.slotapigateway.gateway.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;
import org.wilsonks.slotapigateway.gateway.filter.CorrelationIdFilter;
import org.wilsonks.slotapigateway.gateway.filter.IdentityHeaderFilter;

import static org.springframework.cloud.gateway.server.mvc.filter.LoadBalancerFilterFunctions.lb;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;
import static org.springframework.web.servlet.function.RequestPredicates.path;

@Configuration
@AllArgsConstructor
public class GatewayConfig {

    private final IdentityHeaderFilter identityHeaderFilter;
    private final CorrelationIdFilter correlationIdFilter;

    @Bean
    RouterFunction<ServerResponse> gatewayRouter() {
        return route("slot-auth-service")
                .route(path("/api/players/**"),http())
                .route(path("/api/employees/**"),http())
                .before(serverRequest -> identityHeaderFilter.apply(serverRequest))
                .before(serverRequest -> correlationIdFilter.apply(serverRequest))
                .filter(lb("SLOT-AUTH-SERVICE"))
                .build();
    }
}
