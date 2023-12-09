package com.trade.monitoring;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
public class MonitoringServiceApplication {

    @Scheduled(fixedRate = 60000)
    public void collectMetrics() {
        // Collect system metrics using Prometheus client library
        // Push metrics to Prometheus gateway
    }
}
