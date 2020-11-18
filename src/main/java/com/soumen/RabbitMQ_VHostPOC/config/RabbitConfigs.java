package com.soumen.RabbitMQ_VHostPOC.config;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.SimpleRoutingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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

}
