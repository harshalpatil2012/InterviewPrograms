package com.trade.main.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("validate-trade", r -> r.path("/trades")
                        .filters(f -> f.rewritePath("/trades", "/validate"))
                        .uri("lb://trade-validation-service"))
                .route("process-trade", r -> r.path("/trades/{tradeId}")
                        .filters(f -> f.rewritePath("/trades/{tradeId}", "/process/{tradeId}"))
                        .uri("lb://trade-processing-service"))
                .build();
    }
}