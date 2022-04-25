package epam.patapchuk.activemq.requestreply.service;

import epam.patapchuk.activemq.requestreply.entity.Car;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;

@Service
@Slf4j
public class Producer {

    public static Integer count = 1;
    public static final String NAME = "carName";
    @Autowired
    private JmsTemplate template;
    @Autowired private Queue mainQueue;

    @Scheduled(fixedRate = 7000)
    public void sending() throws JMSException {
        log.info("Sending from producer car with id = " + count);
        Message reply = template.sendAndReceive(mainQueue, message -> message.createObjectMessage(new Car(count++, NAME)));
        if (reply instanceof ActiveMQObjectMessage replyCar) {
            log.info("Producer: car was processed {}", replyCar.getObject());
        }
    }

}
