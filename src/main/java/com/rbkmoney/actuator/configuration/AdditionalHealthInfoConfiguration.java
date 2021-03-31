package com.rbkmoney.actuator.configuration;

import com.rbkmoney.actuator.indicators.ServiceVersionHealthIndicator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.info.InfoEndpoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "management.endpoint.health.show-details", havingValue = "always")
public class AdditionalHealthInfoConfiguration {

    @Bean
    public HealthIndicator serviceVersionHealthIndicator(
            InfoEndpoint infoEndpoint,
            @Value("${spring.application.name}") String applicationName) {
        return new ServiceVersionHealthIndicator(infoEndpoint, applicationName);
    }

}
