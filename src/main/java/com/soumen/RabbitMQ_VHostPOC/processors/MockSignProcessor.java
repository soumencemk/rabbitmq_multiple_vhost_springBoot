package com.soumen.RabbitMQ_VHostPOC.processors;

import com.soumen.RabbitMQ_VHostPOC.events.MessageEvent;
import com.soumen.RabbitMQ_VHostPOC.events.ResponseMessage;
import com.soumen.RabbitMQ_VHostPOC.service.PublisherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class MockSignProcessor {

    private final PublisherService publisherService;

    public void processSign(MessageEvent event) {
        log.info("Processing from - " + event.getClientId());
        ResponseMessage responseMessage;
        if (event.getClientId().contains("dmm")) {
            responseMessage = ResponseMessage.newDMMResponseMessage(event.getMessage() + "_processed");
        } else {
            responseMessage = ResponseMessage.newPGAResponseMessage(event.getMessage() + "_processed");
        }
        publisherService.publish(responseMessage);
    }
}
