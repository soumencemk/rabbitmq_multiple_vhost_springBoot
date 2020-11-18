package com.soumen.RabbitMQ_VHostPOC.service;

import com.soumen.RabbitMQ_VHostPOC.events.MessageEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.connection.SimpleResourceHolder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class PublisherServiceImpl implements PublisherService {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void publish(MessageEvent event) {
        try {
            SimpleResourceHolder.bind(rabbitTemplate.getConnectionFactory(), event.getClientId());
            rabbitTemplate.convertAndSend(event.getExchange(), event.getRoutingKey(), event.getMessage());
        } catch (Exception e) {
            log.error("Exception ", e);
        } finally {
            SimpleResourceHolder.unbindIfPossible(rabbitTemplate.getConnectionFactory());
        }

    }
}
