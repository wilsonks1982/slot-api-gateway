# Slot API Gateway Server 

## Stack
1. [ ] Spring Boot 3.5.16
2. [ ] Spring Cloud 2025.0.1
3. [ ] Java 21
4. [ ] Spring Cloud Gateway MVC 4.3.3
5. [ ] Actuator
6. [ ] Prometheus metrics

## Phase 1 - Core Gateway
────────────────────────
### ✔ Step 1 Routing
#### Why? Client need to know one endpoint.
```
@Bean
RouterFunction<ServerResponse> gatewayRouter() {
    return route("slot-auth-service")
            .route(path("/api/players/**"),
                    http("http://localhost:9091"))
            .build();
}
```
### ✔ Step 2 Service Discovery (Eureka)

```
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
```

```
eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka/
    register-with-eureka: true
    fetch-registry: true

  instance:
    prefer-ip-address: true
    instance-id: ${spring.application.name}

```

### ✔ Step 3 Load Balancer

```
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-loadbalancer</artifactId>
</dependency>
```

```
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
```

## Phase 2 - Security
────────────────────────
### Step 4 JWT Authentication
API Gateway becomes the security perimeter of  microservices architecture.
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>

<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-api</artifactId>
    <version>0.12.6</version>
</dependency>

<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-impl</artifactId>
    <version>0.12.6</version>
    <scope>runtime</scope>
</dependency>

<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-jackson</artifactId>
    <version>0.12.6</version>
    <scope>runtime</scope>
</dependency>
```
#### SecurityConfig - Security Policy
```
Which URLs are public?

Which URLs require authentication?

Which filter runs first?

Which filter runs after?

#### JwtAuthenticationFilter
#### JwtService


### Step 5 Authorization
### Step 6 Token Relay

## Phase 3 - Request Processing
────────────────────────
### Step 7 Request Transformation
### Step 8 Response Transformation
### Step 9 Response Aggregation

## Phase 4 - Observability
────────────────────────
### Step 10 Correlation ID
### Step 11 MDC Logging
### Step 12 OpenTelemetry
### Step 13 Metrics

## Phase 5 - Reliability
────────────────────────
### Step 14 Timeouts
### Step 15 Retry
### Step 16 Circuit Breaker
### Step 17 Fallback

## Phase 6 - Traffic Management
────────────────────────
### Step 18 Rate Limiting
### Step 19 CORS
### Step 20 Compression
### Step 21 Caching

## Phase 7 - Production
────────────────────────
### Step 22 API Versioning
### Step 23 Maintenance Mode
### Step 24 Blue/Green Deployment
### Step 25 Canary Release