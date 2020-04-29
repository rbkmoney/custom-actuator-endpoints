package com.rbkmoney.actuator.indicators;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.info.InfoEndpoint;

import javax.annotation.PostConstruct;
import java.lang.management.ManagementFactory;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
public class ServiceVersionHealthIndicator implements HealthIndicator {

    private final InfoEndpoint infoEndpoint;

    @Override
    public Health health() {
        return Health.up()
                .withDetail("git.commit", ((Map<String, Object>)((Map<String, Object>)infoEndpoint.info().get("git")).get("commit")).get("id"))
                .withDetail("uptime", getUptimeFormatted(ManagementFactory.getRuntimeMXBean().getUptime()))
                .build();
    }

    @PostConstruct
    public void onStart() {
        log.info("Added service version health indicator");
    }


    protected String getUptimeFormatted(long uptimeMillis) {

        long days = TimeUnit.MILLISECONDS.toDays(uptimeMillis);
        uptimeMillis -= TimeUnit.DAYS.toMillis(days);

        long hours = TimeUnit.MILLISECONDS.toHours(uptimeMillis);
        uptimeMillis -= TimeUnit.HOURS.toMillis(hours);

        long minutes = TimeUnit.MILLISECONDS.toMinutes(uptimeMillis);
        uptimeMillis -= TimeUnit.MINUTES.toMillis(minutes);

        long seconds = TimeUnit.MILLISECONDS.toSeconds(uptimeMillis);

        return String.format("%dd, %dh, %dm, %ds ", days, hours, minutes, seconds);
    }
}
