package org.wilsonks.slotapigateway.gateway.filter;


import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.function.ServerRequest;

@Slf4j
@Component
public class CorrelationIdFilter {

    public static final String CORRELATION_ID_HEADER = "X-Correlation-Id";
    public static final String MDC_KEY = "correlationId";

    public ServerRequest apply(ServerRequest request) {
        String correlationId = request.headers().firstHeader(CORRELATION_ID_HEADER);

        if (correlationId == null || correlationId.isBlank()) {
            log.info("No correlation ID found in request headers. Generating a new one.");
            correlationId = java.util.UUID.randomUUID().toString();
        } else {
            log.info("Correlation ID found in request headers: {}", correlationId);
        }

        MDC.put(MDC_KEY, correlationId);

        return ServerRequest.from(request)
                .header(CORRELATION_ID_HEADER, correlationId)
                .build();


    }
}
