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
### ✔ Step 2 Service Discovery
### ✔ Step 3 Load Balancing (Spring Cloud LoadBalancer)
### ✔ Step 4 JWT Authentication - Symmetric Key HS256(HmacSHA256)
### ✔ Step 5 Identity Propagation 

## Phase 2 - Observability
────────────────────────
### Step 6 Correlation ID
### Step 7 MDC Logging
### Step 8 OpenTelemetry
### Step 9 Metrics

## Phase 3 - Reliability
────────────────────────
### Step 10 Timeouts
### Step 11 Retry
### Step 12 Circuit Breaker
### Step 13 Fallback

## Phase 4 - Traffic Management
────────────────────────
### Step 14 Rate Limiting
### Step 15 CORS
### Step 16 Compression
### Step 17 Caching

## Phase 5 - Production
────────────────────────
### Step 18 API Versioning
### Step 19 Maintenance Mode
### Step 20 Blue/Green Deployment
### Step 21 Canary Release