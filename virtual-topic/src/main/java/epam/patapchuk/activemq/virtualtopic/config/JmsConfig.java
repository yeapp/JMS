package epam.patapchuk.activemq.virtualtopic.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.jms.Queue;

@Configuration
@EnableScheduling
public class JmsConfig {

    @Value("${spring.activemq.broker-url}")
    private String broker_url;

    @Value("${spring.activemq.user}")
    private String username;

    @Value("${spring.activemq.password}")
    private String password;

    @Value("${spring.activemq.subDomain}")
    private boolean subDomain;

    @Value("${spring.activemq.queue}")
    private String queue;

    @Bean
    public ActiveMQConnectionFactory connectionFactory() {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
        factory.setBrokerURL(broker_url);
        factory.setUserName(username);
        factory.setPassword(password);
        return factory;
    }

    @Bean
    JmsTemplate template() {
        return new JmsTemplate(connectionFactory());
    }

    @Bean
    Queue mainQueue() {
        return new ActiveMQQueue(queue);
    }

    @Bean
    DefaultJmsListenerContainerFactory queueListenerContainerFactory() {
        return listener(subDomain);
    }

    @Bean
    DefaultJmsListenerContainerFactory topicListenerContainerFactory() {
        return listener(!subDomain);
    }


    private DefaultJmsListenerContainerFactory listener(boolean isSubDomain) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        factory.setPubSubDomain(isSubDomain);
        return factory;
    }

}