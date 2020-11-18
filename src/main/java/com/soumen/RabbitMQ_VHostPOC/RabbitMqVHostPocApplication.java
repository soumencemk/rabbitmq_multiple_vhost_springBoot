package com.soumen.RabbitMQ_VHostPOC;

import com.soumen.RabbitMQ_VHostPOC.events.SignMessage;
import com.soumen.RabbitMQ_VHostPOC.service.PublisherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
@Log4j2
@RequiredArgsConstructor
public class RabbitMqVHostPocApplication implements CommandLineRunner {

    private final PublisherService publisher;

    public static void main(String[] args) {
        SpringApplication.run(RabbitMqVHostPocApplication.class, args);
    }


    @EventListener(ApplicationStartedEvent.class)
    public void onStartup() {
        SignMessage dmm_sign_1 = SignMessage.newDMMSignMessage("dmm_sign_1");
        publisher.publish(dmm_sign_1);

        SignMessage pga_sign_1 = SignMessage.newPGASignMessage("pga_sign_1");
        publisher.publish(pga_sign_1);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("you can press Ctrl+C to shutdown application");
        Thread.currentThread().join();
    }
}
