package org.daniel.mq.publisher.config;

import org.daniel.mq.publisher.hello.constant.HelloQueue;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {
    @Value("${spring.rabbitmq.host}")
    private String host;
    @Value("${spring.rabbitmq.username}")
    private String username;
    @Value("${spring.rabbitmq.password}")
    private String password;
    @Value("${spring.rabbitmq.port}")
    private int port;
    @Value("${spring.rabbitmq.virtual:/}")
    private String virtualHost;

    //default queue
    @Bean
    Queue helloQueue() {
        return new Queue(HelloQueue.class.getSimpleName(), false);
    }

    //default exchange
    @Bean
    DirectExchange worldExchange() {
        return new DirectExchange(HelloQueue.WORLD_EXCHANGE.toString());
    }
    @Bean
    DirectExchange rabbitExchange() {
        return new DirectExchange(HelloQueue.RABBIT_EXCHANGE.toString());
    }

    //default binding
    @Bean
    Binding worldQueueBinding(@Qualifier("worldExchange") DirectExchange directExchange, @Qualifier("helloQueue") Queue queue) {
        return BindingBuilder.bind(queue).to(directExchange).with(HelloQueue.WORLD_EXCHANGE.getRoutingKey());
    }
    @Bean
    Binding rabbitQueueBinding(@Qualifier("rabbitExchange") DirectExchange directExchange, @Qualifier("helloQueue") Queue queue) {
        return BindingBuilder.bind(queue).to(directExchange).with(HelloQueue.RABBIT_EXCHANGE.getRoutingKey());
    }

    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setPort(port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(virtualHost);
        return connectionFactory;
    }

    @Bean
    MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
