package com.soumen.RabbitMQ_VHostPOC.events;

public interface MessageEvent {

    String getClientId();
    String getExchange();
    String getRoutingKey();
    String getMessage();
}
