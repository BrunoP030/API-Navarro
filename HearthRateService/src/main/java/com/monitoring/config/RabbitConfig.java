package com.monitoring.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitConfig {

    public static final String MONITORING_EXCHANGE = "monitoring";
    public static final String HEARTRATE_QUEUE = "heartrate.queue";
    public static final String HEARTRATE_ROUTING_KEY = "monitoring.heartRate";

    @Bean
    public TopicExchange monitoringExchange() {
        return new TopicExchange(MONITORING_EXCHANGE, true, false);
    }

    @Bean
    public Queue heartRateQueue() {
        return QueueBuilder.durable(HEARTRATE_QUEUE).build();
    }

    @Bean
    public Binding heartRateBinding() {
        return BindingBuilder
                .bind(heartRateQueue())
                .to(monitoringExchange())
                .with(HEARTRATE_ROUTING_KEY);
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