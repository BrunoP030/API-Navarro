package com.monitoring.service;

import com.monitoring.config.RabbitConfig;
import com.monitoring.model.HeartRateData;
import com.monitoring.model.BloodPressureData;
import com.monitoring.model.PatientData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class PatientPublisherService {

    private static final Logger logger = LoggerFactory.getLogger(PatientPublisherService.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private final Random random = new Random();
    private final String[] patientNames = {
            "João Silva"
    };

    public void publishPatientData(PatientData patientData) {
        try {
            // Separa os dados em HeartRate e BloodPressure
            publishHeartRateData(patientData);

            logger.info("Dados completos do paciente publicados para ambas as filas: {}", patientData);

        } catch (Exception e) {
            logger.error("Erro ao publicar dados do paciente: {}", e.getMessage(), e);
            throw e;
        }
    }

    private void publishHeartRateData(PatientData patientData) {
        try {
            // Cria dados específicos para Heart Rate usando construtor
            PatientData heartRateData = new PatientData(
                    patientData.getPatientId(),
                    patientData.getPatientName(),
                    patientData.getHeartRate(),
                    patientData.getSystolicPressure(),
                    patientData.getDiastolicPressure()
            );

            // Publica para a fila de Heart Rate
            rabbitTemplate.convertAndSend(
                    RabbitConfig.MONITORING_EXCHANGE,
                    RabbitConfig.HEARTRATE_ROUTING_KEY, // "monitoring.heartRate"
                    patientData
            );

            logger.info("HeartRate data publicado: {}", heartRateData);
        } catch (Exception e) {
            logger.error("Erro ao publicar HeartRate data: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Scheduled(fixedRate = 15000) // Executa a cada 15 segundos
    public void generateAndPublishPatientData() {
        try {
            PatientData patientData = generateRandomPatientData();
            publishPatientData(patientData);
            logger.info("Dados aleatórios de paciente gerados e publicados: {}", patientData);
        } catch (Exception e) {
            logger.error("Erro ao gerar e publicar dados aleatórios do paciente: {}", e.getMessage(), e);
        }
    }


    private PatientData generateRandomPatientData() {
        String patientId = "PAT-" + 1555;
        String patientName = patientNames[random.nextInt(patientNames.length)];
        Integer heartRate = 55; // 50-150 bpm
        Integer systolicPressure = 80; // 80-180 mmHg
        Integer diastolicPressure = 50; // 50-110 mmHg

        return new PatientData(patientId, patientName, heartRate, systolicPressure, diastolicPressure);
    }
}