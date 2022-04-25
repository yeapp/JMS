package epam.patapchuk.activemq.virtualtopic.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.jms.Message;
import javax.jms.MessageNotWriteableException;
import javax.jms.Queue;

@Service
@Slf4j
public class Producer {

    @Autowired
    private JmsTemplate template;
    @Autowired
    private Queue queue;

    @Scheduled(fixedRate = 10000)
    public void sendMessage() throws MessageNotWriteableException {
        log.info("sending message to virtual queue");
        template.convertAndSend(queue, create("message from virtual queue"));
    }

    private Message create(String text) throws MessageNotWriteableException {
        ActiveMQTextMessage message = new ActiveMQTextMessage();
        message.setText(text);
        return message;
    }

}

