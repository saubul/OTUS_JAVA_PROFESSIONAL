package ru.otus.java.pro.mt.core.transfers.integrations.notifications;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.otus.java.pro.mt.core.transfers.configs.properties.NotificationsIntegrationProperties;

@Component
@RequiredArgsConstructor
public class NotificationsIntegrationService {

    private final KafkaTemplate<String, ru.otus.java.pro.mt.core.transfers.avro.model.TransferStatus.TransferStatus> kafkaTemplate;

    private final NotificationsIntegrationProperties notificationsIntegrationProperties;

    public void sendNotification(ru.otus.java.pro.mt.core.transfers.avro.model.TransferStatus.TransferStatus transferStatus) {
        kafkaTemplate.send(notificationsIntegrationProperties.getTransferStatusTopic(), transferStatus);
    }

}
