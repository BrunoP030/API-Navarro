package com.monitoring.service;

import com.monitoring.config.RabbitConfig;
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
            "João Silva", "Maria Santos", "Pedro Costa", "Ana Oliveira", "Carlos Pereira",
            "Roberto Almeida", "Fernanda Lima", "Marcos Rocha", "Juliana Freitas", "Ricardo Barros"
    };

    public void publishPatientData(PatientData patientData) {
        try {
            // Cria dados específicos para Heart Rate (incluindo dados completos do paciente)
            HeartRateData heartRateData = new HeartRateData(patientData);

            // Publica APENAS dados de Heart Rate para iniciar o fluxo
            rabbitTemplate.convertAndSend(
                    RabbitConfig.MONITORING_EXCHANGE,
                    RabbitConfig.HEARTRATE_ROUTING_KEY,
                    heartRateData
            );

            logger.info("Dados do paciente publicados para HeartRate queue: {}", heartRateData);

        } catch (Exception e) {
            logger.error("Erro ao publicar dados do paciente: {}", e.getMessage(), e);
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
        String patientId = "PAT-" + String.format("%04d", random.nextInt(1000));
        String patientName = patientNames[random.nextInt(patientNames.length)];
        Integer heartRate = 50 + random.nextInt(100); // 50-150 bpm (incluindo casos extremos)
        Integer systolicPressure = 80 + random.nextInt(100); // 80-180 mmHg
        Integer diastolicPressure = 50 + random.nextInt(60); // 50-110 mmHg

        return new PatientData(patientId, patientName, heartRate, systolicPressure, diastolicPressure);
    }
}