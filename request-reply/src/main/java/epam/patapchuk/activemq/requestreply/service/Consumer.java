package epam.patapchuk.activemq.requestreply.service;

import epam.patapchuk.activemq.requestreply.entity.Car;
import epam.patapchuk.activemq.requestreply.exception.NonexistentObjectException;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.listener.adapter.JmsResponse;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;

import static org.springframework.jms.listener.adapter.JmsResponse.forDestination;

@Service
@Slf4j
public class Consumer {

    @JmsListener(destination = "${spring.activemq.queue}", containerFactory = "listenerContainerFactory")
    public JmsResponse<Message> info(Message message) throws JMSException {
        if (message instanceof ActiveMQObjectMessage car) {
            log.info("New message from queue with " + car.getObject());
            log.info("Send reply: " + message.getJMSReplyTo());
            return forDestination(convert(car), message.getJMSReplyTo());
        }
        throw new NonexistentObjectException();
    }

    private Message convert(ActiveMQObjectMessage message) throws JMSException {
        ActiveMQObjectMessage reply = new ActiveMQObjectMessage();
        Car origin = (Car) message.getObject();
        reply.setObject(new Car(origin.id(), origin.name() + "_mark"));
        return reply;
    }

}
