package ru.otus.config;

import jakarta.jms.ConnectionFactory;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;

@EnableJms
@Configuration
public class ActiveMQConfig {

    private final static String CONNECTION_FACTORY = "activeMQConnectionFactory";

    public final static String JMS_CONTAINER_FACTORY = "activeMQContainerFactory";

    public final static String QUEUE_NAME = "test.queue";

    @Bean(CONNECTION_FACTORY)
    public ConnectionFactory connectionFactory() {
        return new ActiveMQConnectionFactory("tcp://localhost:61616");
    }

    @Bean(JMS_CONTAINER_FACTORY)
    public DefaultJmsListenerContainerFactory myFactory(ConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        return factory;
    }

}
