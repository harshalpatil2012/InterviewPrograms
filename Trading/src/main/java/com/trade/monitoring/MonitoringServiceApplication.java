package com.trade.monitoring;

import io.prometheus.client.Counter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
public class MonitoringServiceApplication {

    private static final String PROMETHEUS_GATEWAY_URL = "http://prometheus-gateway-url:9091";

    private final Counter requests = Counter.build()
            .name("requests_total")
            .help("Total number of requests.")
            .register();

    public static void main(String[] args) {
        SpringApplication.run(MonitoringServiceApplication.class, args);
    }

    @Scheduled(fixedRate = 60000) // Run every 60 seconds
    public void collectMetrics() {
        // Simulate collecting metrics
        requests.inc();

        // Push metrics to Prometheus gateway
        pushMetricsToPrometheus();
    }

    private void pushMetricsToPrometheus() {
        // PushGateway pushGateway = new PushGateway(PROMETHEUS_GATEWAY_URL);

        try {
            // pushGateway.pushAdd(CollectorRegistry.defaultRegistry, "job-name");
        } catch (Exception e) {
            // Handle exception, e.g., log an error
            e.printStackTrace();
        }
    }
}
