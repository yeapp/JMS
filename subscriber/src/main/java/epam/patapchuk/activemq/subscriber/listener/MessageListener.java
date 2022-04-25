package epam.patapchuk.activemq.subscriber.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MessageListener {

    @JmsListener(destination = "${spring.activemq.topic}", containerFactory = "durableJmsListenerContainerFactory")
    public void getDurable(String message) {
        log.info("Durable listener get: " + message);
    }

    @JmsListener(destination = "${spring.activemq.topic}", containerFactory = "nonDurableJmsListenerContainerFactory")
    public void getNonDurable(String message) {
        log.info("Non Durable listener get: " + message);
    }
}
