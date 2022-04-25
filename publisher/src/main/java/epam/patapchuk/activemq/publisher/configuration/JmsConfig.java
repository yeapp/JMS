package epam.patapchuk.activemq.publisher.configuration;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTempTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.DeliveryMode;
import javax.jms.Topic;

@Configuration
public class JmsConfig {
    @Value("${spring.activemq.broker-url}")
    private String broker_url;

    @Value("${spring.activemq.user}")
    private String username;

    @Value("${spring.activemq.password}")
    private String password;

    @Value("${spring.activemq.subDomain}")
    private boolean subDomain;

    @Value("${spring.activemq.topic}")
    private String topic;

    @Bean
    public ActiveMQConnectionFactory connectionFactory() {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
        factory.setBrokerURL(broker_url);
        factory.setUserName(username);
        factory.setPassword(password);
        return factory;
    }

    @Bean
    public Topic topic() {
        return new ActiveMQTempTopic(topic);
    }

    @Bean
    public JmsTemplate template() {
        JmsTemplate template = new JmsTemplate(connectionFactory());
        template.setDeliveryMode(DeliveryMode.PERSISTENT);
        template.setPubSubDomain(subDomain);
        return template;
    }

}
