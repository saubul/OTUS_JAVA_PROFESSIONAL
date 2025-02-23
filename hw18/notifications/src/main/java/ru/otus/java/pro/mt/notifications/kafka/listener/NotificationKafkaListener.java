package ru.otus.java.pro.mt.notifications.kafka.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class NotificationKafkaListener {

    private final static Logger LOGGER = Logger.getLogger(NotificationKafkaListener.class.getName());

    @KafkaListener(topics = "${notifications.kafka.topics.transfers-status}")
    public void consumeNotification(ru.otus.java.pro.mt.core.transfers.avro.model.TransferStatus.TransferStatus transferStatus) {
        LOGGER.info(String.format("По переводу %s клиенту отправлена нотификация", transferStatus.getTransferId()));
    }

}
