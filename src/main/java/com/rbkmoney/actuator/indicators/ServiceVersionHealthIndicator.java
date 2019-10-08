package com.rbkmoney.actuator.indicators;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.info.InfoEndpoint;

import javax.annotation.PostConstruct;
import java.lang.management.ManagementFactory;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class ServiceVersionHealthIndicator implements HealthIndicator {

    private final InfoEndpoint infoEndpoint;

    @Override
    public Health health() {
        return Health.up()
                .withDetail("git.commit", ((Map<String, Object>)((Map<String, Object>)infoEndpoint.info().get("git")).get("commit")).get("id"))
                .withDetail("uptime", ManagementFactory.getRuntimeMXBean().getUptime() / 1000 + "sec")
                .build();
    }

    @PostConstruct
    public void onStart() {
        log.info("Added service version health indicator");
    }
}
