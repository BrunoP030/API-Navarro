package com.monitoring.subscriber.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String MONITORING_EXCHANGE = "monitoring";
    public static final String MONITORING_QUEUE = "monitoring.queue";
    public static final String MONITORING_ROUTING_KEY = "monitoring.final";

    @Bean
    public TopicExchange monitoringExchange() {
        return ExchangeBuilder
                .topicExchange(MONITORING_EXCHANGE)
                .durable(true)
                .build();
    }

    @Bean
    public Queue monitoringQueue() {
        return QueueBuilder
                .durable(MONITORING_QUEUE)
                .build();
    }

    @Bean
    public Binding monitoringBinding() {
        return BindingBuilder
                .bind(monitoringQueue())
                .to(monitoringExchange())
                .with(MONITORING_ROUTING_KEY); // Escuta tanto heartRate quanto bloodPressure
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES, false);

        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter(objectMapper);

        converter.setAlwaysConvertToInferredType(true);

        return converter;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        template.setExchange(MONITORING_EXCHANGE);

        template.setMandatory(true);
        template.setConfirmCallback((correlationData, ack, cause) -> {
            if (!ack) {
                System.err.println("Message not delivered: " + cause);
            }
        });

        return template;
    }
}