package com.monitoring.subscriber.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String MONITORING_EXCHANGE = "monitoring";
    public static final String MONITORING_QUEUE = "monitoring.analysis";

    @Bean
    public TopicExchange monitoringExchange() {
        return new TopicExchange(MONITORING_EXCHANGE);
    }

    @Bean
    public Queue monitoringQueue() {
        return QueueBuilder.durable(MONITORING_QUEUE).build();
    }

    @Bean
    public Binding monitoringBinding() {
        return BindingBuilder
                .bind(monitoringQueue())
                .to(monitoringExchange())
                .with("monitoring.*"); // Escuta tanto heartRate quanto bloodPressure
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }
}