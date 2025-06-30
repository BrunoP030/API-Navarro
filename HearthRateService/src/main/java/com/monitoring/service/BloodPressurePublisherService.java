package com.monitoring.service;

import com.monitoring.model.HeartRateData;
import com.monitoring.model.PatientData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BloodPressurePublisherService {

    private static final Logger logger = LoggerFactory.getLogger(BloodPressurePublisherService.class);

    private static final String MONITORING_EXCHANGE = "monitoring";
    private static final String BLOODPRESSURE_ROUTING_KEY = "monitoring.bloodPressure";

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void publishToBloodPressure(PatientData patientData) {
        try {
            logger.info("Publishing to BloodPressure service for patient: {}", patientData.getPatientId());

            rabbitTemplate.convertAndSend(
                    MONITORING_EXCHANGE,
                    BLOODPRESSURE_ROUTING_KEY,
                    patientData
            );

            logger.info("Successfully published to BloodPressure queue for patient: {}", patientData.getPatientId());

        } catch (Exception e) {
            logger.error("Error publishing to BloodPressure queue for patient {}: {}",
                    patientData.getPatientId(), e.getMessage(), e);
            throw e;
        }
    }
}