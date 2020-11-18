package com.soumen.RabbitMQ_VHostPOC.service;

import com.soumen.RabbitMQ_VHostPOC.events.MessageEvent;

public interface PublisherService {
    void publish(MessageEvent event);
}
