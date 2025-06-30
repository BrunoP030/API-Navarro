package com.monitoring.service;

import com.monitoring.config.RabbitConfig;
import com.monitoring.model.PatientData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MonitoringPublisher {

    private static final Logger logger = LoggerFactory.getLogger(MonitoringPublisher.class);

    private static final String MONITORING_EXCHANGE = "monitoring";
    private static final String MONITORING_ROUTING_KEY = "monitoring.final";

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void publishToMonitoring(PatientData patientData) {
        try {
            logger.info("Publishing message to Monitoring service for patient: {}", patientData.getPatientId());

            rabbitTemplate.convertAndSend(
                    MONITORING_EXCHANGE,
                    MONITORING_ROUTING_KEY,
                    patientData
            );

            logger.info("Successfully published message to Monitoring service for patient: {}", patientData.getPatientId());

        } catch (Exception e) {
            logger.error("Failed to publish message to Monitoring service for patient {}: {}",
                    patientData.getPatientId(), e.getMessage(), e);
            throw e;
        }
    }
}