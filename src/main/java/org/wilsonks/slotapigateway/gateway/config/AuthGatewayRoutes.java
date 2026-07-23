package org.wilsonks.slotapigateway.gateway.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;
import org.wilsonks.slotapigateway.gateway.filter.GatewayRequestHeaderFilter;

import java.net.URI;

import static org.springframework.cloud.gateway.server.mvc.filter.CircuitBreakerFilterFunctions.circuitBreaker;
import static org.springframework.cloud.gateway.server.mvc.filter.LoadBalancerFilterFunctions.lb;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;
import static org.springframework.web.servlet.function.RequestPredicates.path;

@Configuration
@AllArgsConstructor
public class AuthGatewayRoutes {

    private final GatewayRequestHeaderFilter gatewayRequestHeaderFilter;

    @Bean
    RouterFunction<ServerResponse> gatewayRouter() {
        return route("slot-auth-service")
                .route(path("/api/chaos/**"),http())
                .route(path("/api/players/**"),http())
                .route(path("/api/employees/**"),http())
                .before(serverRequest -> gatewayRequestHeaderFilter.apply(serverRequest))
                .filter(lb("SLOT-AUTH-SERVICE"))
                .filter(circuitBreaker(
                        "auth-service",
                        URI.create("forward:/fallback/auth")))
                .build();
    }
}
