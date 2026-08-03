package org.wilsonks.slotapigateway.gateway.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;
import org.wilsonks.slotapigateway.gateway.filter.GatewayRequestHeaderFilter;

import static org.springframework.cloud.gateway.server.mvc.filter.CircuitBreakerFilterFunctions.circuitBreaker;
import static org.springframework.cloud.gateway.server.mvc.filter.LoadBalancerFilterFunctions.lb;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;
import static org.springframework.web.servlet.function.RequestPredicates.path;

@Configuration
@AllArgsConstructor
public class GatewayRoutes {

    private final GatewayRequestHeaderFilter gatewayRequestHeaderFilter;

    @Bean
    RouterFunction<ServerResponse> appGatewayRouter() {

        // 1. Auth Service routes
        RouterFunction<ServerResponse> authRoutes = route("slot-auth-service")
                .route(path("/api/players/**"), http())
                .route(path("/api/employees/**"), http())
                .before(gatewayRequestHeaderFilter::apply)
                .filter(circuitBreaker(config -> config
                        .setId("auth-service")
                        .setFallbackUri("forward:/fallback/auth")))
                .filter(lb("slot-auth-service"))
                .build();

        // 2. Bank Service routes
        RouterFunction<ServerResponse> bankRoutes = route("slot-bank-service")
                .route(path("/api/accounts/**"), http())
                .before(gatewayRequestHeaderFilter::apply)
                .filter(circuitBreaker(config -> config
                        .setId("bank-service")
                        .setFallbackUri("forward:/fallback/bank")))
                .filter(lb("slot-bank-service"))
                .build();

        // 3. Floor Management Service routes
        RouterFunction<ServerResponse> floorManagementRoutes = route("slot-floor-management-service")
                .route(path("/api/floor/**"), http())
                .before(gatewayRequestHeaderFilter::apply)
                .filter(circuitBreaker(config -> config
                        .setId("floor-management-service")
                        .setFallbackUri("forward:/fallback/floor")))
                .filter(lb("slot-floor-management-service"))
                .build();

        // 3. combine all routes
        return authRoutes.and(bankRoutes).and(floorManagementRoutes);
    }
}
