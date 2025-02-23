package ru.otus.java.pro.mt.core.transfers.configs;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import ru.otus.java.pro.mt.core.transfers.configs.properties.LimitsIntegrationProperties;
import ru.otus.java.pro.mt.core.transfers.factory.RestClientFactory;

@Configuration
@RequiredArgsConstructor
public class RestClientsConfig {

    private final RestClientFactory restClientFactory;

    @Bean
    public RestClient limitsClient(LimitsIntegrationProperties limitsIntegrationProperties) {
        return restClientFactory.createRestClient(limitsIntegrationProperties.getRestClientConfigurationProperties());
    }
}
