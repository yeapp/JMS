package epam.patapchuk.activemq.virtualtopic.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;

@Service
@Slf4j
public class Consumer {

    @JmsListener(destination = "${spring.activemq.consumerQueue}", containerFactory = "queueListenerContainerFactory")
    public void readFirst(ActiveMQTextMessage message) throws JMSException {
        log.info("Read message from queue: queueName={}, message={}",
                message.getJMSDestination().toString(),
                message.getText());
    }

    @JmsListener(destination = "${spring.activemq.consumerTopic}", containerFactory = "topicListenerContainerFactory")
    public void readSecond(ActiveMQTextMessage message) throws JMSException {
        log.info("Read message from topic: topicName={}, message={}",
                message.getJMSDestination().toString(),
                message.getText());
    }

}

