package com.soumen.RabbitMQ_VHostPOC.service;

import com.soumen.RabbitMQ_VHostPOC.events.SignMessage;
import com.soumen.RabbitMQ_VHostPOC.processors.MockSignProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class ConsumerServiceImpl implements ConsumerService {
    private final MockSignProcessor signProcessor;

    @Override
    public void dmmReceiveSignMessage(String message) {
        log.info("Received DMM Sign request - " + message);
        SignMessage signMessage = SignMessage.newDMMSignMessage(message);
        signProcessor.processSign(signMessage);
    }

    @Override
    public void pgaReceiveSignMessage(String message) {
        log.info("Received PGA Sign request - " + message);
        SignMessage signMessage = SignMessage.newPGASignMessage(message);
        signProcessor.processSign(signMessage);
    }
}
