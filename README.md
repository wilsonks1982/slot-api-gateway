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
### Step 6 Correlation ID - Business request identifier (your own ID)
### Step 7 MDC Logging - Log enrichment
### Step 8 OpenTelemetry - Distributed performance tracing
- The first service creates a Trace.
- You never write this header yourself. 
- The OpenTelemetry instrumentation libraries handle it automatically.
- Every downstream HTTP call automatically includes:
    - TraceId
    - SpanId
    - ParentSpanId
- OpenTelemetry doesn't replace your filters, It works alongside them.
- Spring Boot Actuator already provides Micrometer Observation
- The bridge converts Micrometer Observation to OpenTelemetry Spans.
- The exporter sends the Spans to Jaeger.
#### What is an Observation? An Event that is being observed. 
#### Who creates Observations automatically? Spring Boot - Nearly every Spring infrastructure component is already instrumented.
#### Why Observation? Spring Framework emits an Observation, which is vendor-neutral. Handlers decide what to do with it.
#### How does Observation become an OpenTelemetry span? Observation (Publisher), ObservationHandler (Subscriber), OpenTelemetryBridge (Subscriber), OpenTelemetryExporter (Subscriber)
#### How does it also become a Micrometer metric? 
#### Why can one API produce both traces and metrics? 
One API can produce both traces and metrics because Spring Boot Actuator provides Micrometer Observation, which is vendor-neutral. Handlers decide what to do with the Observation, allowing it to be converted into OpenTelemetry Spans and exported to Jaeger for tracing, while also being recorded as Micrometer metrics for monitoring purposes.
Spring publishes an observation, and multiple independent handlers react to it without the publisher knowing who they are.

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