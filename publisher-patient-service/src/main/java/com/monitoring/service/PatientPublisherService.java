package com.monitoring.service;

import com.monitoring.config.RabbitConfig;
import com.monitoring.model.BloodPressureData;
import com.monitoring.model.HeartRateData;
import com.monitoring.model.PatientData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class PatientPublisherService {

    private static final Logger logger = LoggerFactory.getLogger(PatientPublisherService.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private final Random random = new Random();
    private final String[] patientNames = {
            "João Silva", "Maria Santos", "Pedro Costa", "Ana Oliveira", "Carlos Pereira"
    };

    public void publishPatientData(PatientData patientData) {
        try {
            // Cria dados específicos para Heart Rate
            HeartRateData heartRateData = new HeartRateData(patientData);

            // Cria dados específicos para Blood Pressure
            BloodPressureData bloodPressureData = new BloodPressureData(patientData);

            // Publica dados de Heart Rate
            rabbitTemplate.convertAndSend(
                    RabbitConfig.MONITORING_EXCHANGE,
                    RabbitConfig.HEARTRATE_ROUTING_KEY,
                    heartRateData
            );

            logger.info("Dados de frequência cardíaca publicados: {}", heartRateData);

            // Publica dados de Blood Pressure
            rabbitTemplate.convertAndSend(
                    RabbitConfig.MONITORING_EXCHANGE,
                    RabbitConfig.BLOODPRESSURE_ROUTING_KEY,
                    bloodPressureData
            );

            logger.info("Dados publicados sobre pressão arterial: {}", bloodPressureData);

        } catch (Exception e) {
            logger.error("Erro ao publicar dados do paciente: {}", e.getMessage(), e);
        }
    }

    @Scheduled(fixedRate = 10000) // Executa a cada 10 segundos
    public void generateAndPublishPatientData() {
        PatientData patientData = generateRandomPatientData();
        publishPatientData(patientData);
    }

    private PatientData generateRandomPatientData() {
        String patientId = "PAT-" + String.format("%03d", random.nextInt(100));
        String patientName = patientNames[random.nextInt(patientNames.length)];
        Integer heartRate = 60 + random.nextInt(80); // 60-140 bpm
        Integer systolicPressure = 90 + random.nextInt(70); // 90-160 mmHg
        Integer diastolicPressure = 60 + random.nextInt(40); // 60-100 mmHg

        PatientData patientData = new PatientData(patientId, patientName, heartRate,
                                                  systolicPressure, diastolicPressure);
        return patientData;
    }
}