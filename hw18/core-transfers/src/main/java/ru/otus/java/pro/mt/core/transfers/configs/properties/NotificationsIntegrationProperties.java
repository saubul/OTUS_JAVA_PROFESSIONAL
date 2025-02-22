package ru.otus.java.pro.mt.core.transfers.configs.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("integrations.notifications.kafka.topics")
public class NotificationsIntegrationProperties {

    private String transferStatus;

    public String getTransferStatusTopic() {
        return this.transferStatus;
    }

    public void setTransferStatus(String transferStatus) {
        this.transferStatus = transferStatus;
    }
}
