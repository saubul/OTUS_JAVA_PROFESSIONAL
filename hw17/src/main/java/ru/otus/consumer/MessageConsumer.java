package ru.otus.consumer;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

import static ru.otus.config.ActiveMQConfig.JMS_CONTAINER_FACTORY;
import static ru.otus.config.ActiveMQConfig.QUEUE_NAME;

@Component
public class MessageConsumer {

    private final static Logger LOGGER = Logger.getLogger(MessageConsumer.class.getName());

    @JmsListener(destination = QUEUE_NAME, containerFactory = JMS_CONTAINER_FACTORY)
    public void consumeMessage(String message) {
        LOGGER.info("Message consumed: " + message);
    }

}
