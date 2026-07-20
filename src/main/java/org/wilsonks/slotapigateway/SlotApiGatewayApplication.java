package org.wilsonks.slotapigateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@Slf4j
@EnableDiscoveryClient
public class SlotApiGatewayApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SlotApiGatewayApplication.class, args);
        String port = context.getEnvironment().getProperty("server.port");
        log.info("✅ Slot API Gateway is running on port {}", port);
    }

}
