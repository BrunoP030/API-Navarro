package com.monitoring.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    // Constantes para o fluxo completo
    public static final String MONITORING_EXCHANGE = "monitoring.exchange";

    // Filas
    public static final String HEARTRATE_QUEUE = "heartrate.queue";
    public static final String BLOODPRESSURE_QUEUE = "bloodpressure.queue";
    public static final String MONITORING_QUEUE = "monitoring.queue";

    // Routing Keys
    public static final String HEARTRATE_ROUTING_KEY = "monitoring.heartrate";
    public static final String BLOODPRESSURE_ROUTING_KEY = "monitoring.bloodpressure";
    public static final String MONITORING_ROUTING_KEY = "monitoring.final";

    @Bean
    public TopicExchange monitoringExchange() {
        return ExchangeBuilder
                .topicExchange(MONITORING_EXCHANGE)
                .durable(true)
                .build();
    }

    // Fila para HeartRate
    @Bean
    public Queue heartRateQueue() {
        return QueueBuilder
                .durable(HEARTRATE_QUEUE)
                .build();
    }

    // Fila para BloodPressure
    @Bean
    public Queue bloodPressureQueue() {
        return QueueBuilder
                .durable(BLOODPRESSURE_QUEUE)
                .build();
    }

    // Fila para Monitoring
    @Bean
    public Queue monitoringQueue() {
        return QueueBuilder
                .durable(MONITORING_QUEUE)
                .build();
    }

    // Bindings
    @Bean
    public Binding heartRateBinding() {
        return BindingBuilder
                .bind(heartRateQueue())
                .to(monitoringExchange())
                .with(HEARTRATE_ROUTING_KEY);
    }

    @Bean
    public Binding bloodPressureBinding() {
        return BindingBuilder
                .bind(bloodPressureQueue())
                .to(monitoringExchange())
                .with(BLOODPRESSURE_ROUTING_KEY);
    }

    @Bean
    public Binding monitoringBinding() {
        return BindingBuilder
                .bind(monitoringQueue())
                .to(monitoringExchange())
                .with(MONITORING_ROUTING_KEY);
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        ObjectMapper objectMapper = new ObjectMapper();

        // Configurações para maior tolerância na deserialização
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES, false);

        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter(objectMapper);

        // Permitir conversão de tipos relacionados
        converter.setAlwaysConvertToInferredType(true);

        return converter;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        template.setExchange(MONITORING_EXCHANGE);

        // Configurações adicionais para retry e confirmação
        template.setMandatory(true);
        template.setConfirmCallback((correlationData, ack, cause) -> {
            if (!ack) {
                System.err.println("Message not delivered: " + cause);
            }
        });

        return template;
    }
}