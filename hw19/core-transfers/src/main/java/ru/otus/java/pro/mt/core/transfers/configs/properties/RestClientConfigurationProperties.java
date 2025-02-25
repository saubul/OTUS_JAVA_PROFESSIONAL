package ru.otus.java.pro.mt.core.transfers.configs.properties;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;


@NoArgsConstructor
@Data
public class RestClientConfigurationProperties {
    private String url;
    private Duration readTimeout;
    private Duration connectTimeout;
}
