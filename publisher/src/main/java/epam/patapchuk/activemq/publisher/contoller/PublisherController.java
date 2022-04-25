package epam.patapchuk.activemq.publisher.contoller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.Topic;

@Slf4j
@RestController
public class PublisherController {

    @Autowired
    JmsTemplate template;
    @Autowired
    Topic topic;

    @GetMapping
    public void send(@RequestParam String message) {
        log.info("Sending message " + message + " to  topic - " + topic);
        template.convertAndSend(topic, message);
    }
}
