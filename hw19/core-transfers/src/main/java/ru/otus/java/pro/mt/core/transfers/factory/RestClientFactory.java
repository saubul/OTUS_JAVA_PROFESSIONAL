package ru.otus.java.pro.mt.core.transfers.factory;

import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ru.otus.java.pro.mt.core.transfers.configs.properties.RestClientConfigurationProperties;

@Component
public class RestClientFactory {

    public RestClient createRestClient(RestClientConfigurationProperties restClientConfigurationProperties) {
        return RestClient.builder()
                .requestFactory(getClientHttpRequestFactory(restClientConfigurationProperties))
                .baseUrl(restClientConfigurationProperties.getUrl())
                .build();
    }

    private ClientHttpRequestFactory getClientHttpRequestFactory(RestClientConfigurationProperties restClientConfigurationProperties) {
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(restClientConfigurationProperties.getConnectTimeout());
        clientHttpRequestFactory.setConnectionRequestTimeout(restClientConfigurationProperties.getReadTimeout());
        return clientHttpRequestFactory;
    }

}