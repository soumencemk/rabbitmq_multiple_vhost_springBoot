package com.soumen.RabbitMQ_VHostPOC.service;

public interface ConsumerService {
    void dmmReceiveSignMessage(String message);

    void pgaReceiveSignMessage(String message);
}
