package org.wilsonks.slotapigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.filter.LoadBalancerFilterFunctions.lb;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;
import static org.springframework.web.servlet.function.RequestPredicates.path;

@Configuration
public class GatewayConfig {
    @Bean
    RouterFunction<ServerResponse> gatewayRouter() {
        return route("slot-auth-service")
                .route(path("/api/players/**"),http())
                .route(path("/api/employees/**"),http())
                .filter(lb("SLOT-AUTH-SERVICE"))
                .build();
    }
}
