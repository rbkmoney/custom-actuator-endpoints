package com.rbkmoney.actuator.indicators;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.info.InfoEndpoint;

import javax.annotation.PostConstruct;
import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
public class ServiceVersionHealthIndicator implements HealthIndicator {

    private final InfoEndpoint infoEndpoint;

    @Override
    public Health health() {
        return Health.up()
                .withDetail("git.commit", ((Map<String, Object>) ((Map<String, Object>) infoEndpoint.info().get("git")).get("commit")).get("id"))
                .withDetail("uptime", getUptimeFormatted(ManagementFactory.getRuntimeMXBean().getUptime()))
                .withDetail("service", getServiceInfo(((Map<String, Object>) infoEndpoint.info().get("build"))))
                .build();
    }

    @PostConstruct
    public void onStart() {
        log.info("Added service version health indicator");
    }

    private String getUptimeFormatted(long uptimeMillis) {

        long days = TimeUnit.MILLISECONDS.toDays(uptimeMillis);
        uptimeMillis -= TimeUnit.DAYS.toMillis(days);

        long hours = TimeUnit.MILLISECONDS.toHours(uptimeMillis);
        uptimeMillis -= TimeUnit.HOURS.toMillis(hours);

        long minutes = TimeUnit.MILLISECONDS.toMinutes(uptimeMillis);
        uptimeMillis -= TimeUnit.MINUTES.toMillis(minutes);

        long seconds = TimeUnit.MILLISECONDS.toSeconds(uptimeMillis);

        return String.format("%dd, %dh, %dm, %ds", days, hours, minutes, seconds);
    }

    private Map<String, Object> getServiceInfo(Map<String, Object> buildMap) {
        final HashMap<String, Object> serviceInfoMap = new HashMap<>();
        serviceInfoMap.put("name", buildMap.get("artifact"));
        serviceInfoMap.put("version", buildMap.get("version"));
        return serviceInfoMap;
    }
}
