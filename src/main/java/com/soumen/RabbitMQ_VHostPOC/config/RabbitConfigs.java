package com.soumen.RabbitMQ_VHostPOC.config;

import com.soumen.RabbitMQ_VHostPOC.service.ConsumerService;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.SimpleRoutingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitConfigs {

    @Bean
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(connectionFactory());
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        SimpleRoutingConnectionFactory routingConnectionFactory = new SimpleRoutingConnectionFactory();
        Map<Object, ConnectionFactory> targetConnectionFactories = new HashMap<>();
        targetConnectionFactories.put("dmm_vhost", dmm_vhost_ConnectionFactory());
        targetConnectionFactories.put("pga_vhost", pga_vhost_ConnectionFactory());
        routingConnectionFactory.setTargetConnectionFactories(targetConnectionFactories);
        return routingConnectionFactory;
    }

    private ConnectionFactory dmm_vhost_ConnectionFactory() {

        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setVirtualHost("dmm_vhost");
        connectionFactory.setUsername("dmmRabbitUser");
        connectionFactory.setPassword("guest");
        return connectionFactory;
    }

    private ConnectionFactory pga_vhost_ConnectionFactory() {

        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setVirtualHost("pga_vhost");
        connectionFactory.setUsername("pgaRabbitUser");
        connectionFactory.setPassword("guest");
        return connectionFactory;
    }

    @Bean("dmmSignListener")
    MessageListenerAdapter dmmListenerAdapter(ConsumerService consumerService) {
        return new MessageListenerAdapter(consumerService, "dmmReceiveSignMessage");
    }
    @Bean("pgaSignListener")
    MessageListenerAdapter pgaListenerAdapter(ConsumerService consumerService) {
        return new MessageListenerAdapter(consumerService, "pgaReceiveSignMessage");
    }

    @Bean("dmmSignListenerContainer")
    public SimpleMessageListenerContainer dmmSignListenerContainer(@Qualifier("dmmSignListener") MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(dmm_vhost_ConnectionFactory());
        container.setQueueNames("sv_sign");
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean("pgaSignListenerContainer")
    public SimpleMessageListenerContainer pgaSignListenerContainer(@Qualifier("pgaSignListener") MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(pga_vhost_ConnectionFactory());
        container.setQueueNames("sv_sign");
        container.setMessageListener(listenerAdapter);
        return container;
    }
}
