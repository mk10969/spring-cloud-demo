package com.example.spring.cloud.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("slow", r -> r.path("/api/v1/**")
                        .filters(f -> f.rewritePath("/api/v1/(?<segment>.*))", "/${segment}"))
                        .uri("lb://circuit-breaker-service"))
                .route("client", r -> r.path("/api/v2/**")
                        .filters(f -> f.rewritePath("/api/v2/(?<segment>.*))", "/${segment}"))
                        .uri("lb://web1-service"))
                .build();
    }

}
