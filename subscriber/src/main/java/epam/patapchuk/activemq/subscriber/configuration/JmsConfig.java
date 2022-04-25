package epam.patapchuk.activemq.subscriber.configuration;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTempTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;

import javax.jms.Topic;

@Configuration
public class JmsConfig {

    private static final String DURABLE_CLIENT_ID = "DURABLE_ID";
    private static final String NON_DURABLE_CLIENT_ID = "NON_DURABLE_ID";

    @Value("${spring.activemq.broker-url}")
    private String broker_url;

    @Value("${spring.activemq.user}")
    private String username;

    @Value("${spring.activemq.password}")
    private String password;

    @Value("${spring.activemq.subDomain}")
    private boolean subDomain;

    @Value("${spring.activemq.isDurable}")
    private boolean isDurable;

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
    DefaultJmsListenerContainerFactory durableJmsListenerContainerFactory() {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        factory.setPubSubDomain(subDomain);
        factory.setSubscriptionDurable(isDurable);
        factory.setClientId(DURABLE_CLIENT_ID);
        return factory;
    }

    @Bean
    DefaultJmsListenerContainerFactory nonDurableJmsListenerContainerFactory() {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        factory.setPubSubDomain(subDomain);
        factory.setSubscriptionDurable(!isDurable);
        factory.setClientId(NON_DURABLE_CLIENT_ID);
        return factory;
    }

    @Bean
    Topic topic() {
        return new ActiveMQTempTopic(topic);
    }

}
