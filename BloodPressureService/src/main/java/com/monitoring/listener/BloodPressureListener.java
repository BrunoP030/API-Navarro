package com.monitoring.listener;

import com.monitoring.config.RabbitConfig;
import com.monitoring.model.BloodPressureData;
import com.monitoring.model.PatientData;
import com.monitoring.service.BloodPressureHttpService;
import com.monitoring.service.MonitoringPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BloodPressureListener {

    private static final Logger logger = LoggerFactory.getLogger(BloodPressureListener.class);

    @Autowired
    private BloodPressureHttpService bloodPressureHttpService;

    @Autowired
    private MonitoringPublisher monitoringPublisher;

    @RabbitListener(queues = RabbitConfig.BLOODPRESSURE_QUEUE)
    public void handleBloodPressureMessage(PatientData patientData) {
        try {
            logger.info("Received BloodPressure message for patient: {}", patientData.getPatientId());
            logger.debug("Patient data received: {}", patientData);

            // Validar se os dados de pressão arterial estão presentes
            if (!hasBloodPressureData(patientData)) {
                logger.warn("Patient {} does not have blood pressure data. Skipping blood pressure processing.",
                        patientData.getPatientId());

                // Ainda assim, encaminha para o próximo serviço
                monitoringPublisher.publishToMonitoring(patientData);
                return;
            }

            // Converte PatientData para BloodPressureData
            BloodPressureData bloodPressureData = convertToBloodPressureData(patientData);

            // Processa os dados localmente
            processBloodPressureData(bloodPressureData);

            // Envia os dados para a API BloodPressure
            bloodPressureHttpService.sendBloodPressureData(bloodPressureData);

            PatientData patientData1 = convertToPatientData(patientData);

            // Publica mensagem para o próximo serviço (Monitoring)
            monitoringPublisher.publishToMonitoring(patientData1);

            logger.info("BloodPressure processing completed for patient: {}", patientData.getPatientId());

        } catch (Exception e) {
            logger.error("Error processing BloodPressure message for patient {}: {}",
                    patientData != null ? patientData.getPatientId() : "unknown",
                    e.getMessage(), e);
            throw e; // Re-throw para ativar retry mechanism
        }
    }

    private boolean hasBloodPressureData(PatientData patientData) {
        return patientData.getSystolicPressure() != null &&
                patientData.getDiastolicPressure() != null &&
                patientData.getSystolicPressure() > 0 &&
                patientData.getDiastolicPressure() > 0;
    }

    private BloodPressureData convertToBloodPressureData(PatientData patientData) {
        BloodPressureData bloodPressureData = new BloodPressureData(
                patientData.getPatientId(),
                patientData.getSystolicPressure(),
                patientData.getDiastolicPressure()
        );

        // Classificação da pressão arterial
        bloodPressureData.setClassification(classifyBloodPressure(
                patientData.getSystolicPressure(),
                patientData.getDiastolicPressure()
        ));

        logger.debug("Converted to BloodPressureData: {}", bloodPressureData);
        return bloodPressureData;
    }

    private PatientData convertToPatientData(PatientData patientData) {
        PatientData patientData1 = new PatientData(
                patientData.getPatientId(),
                patientData.getPatientName(),
                patientData.getHeartRate(),
                patientData.getSystolicPressure(),
                patientData.getDiastolicPressure(),
                patientData.getRiskLevel()
        );

        return patientData1;
    }

    private void processBloodPressureData(BloodPressureData bloodPressureData) {
        // Validação e processamento dos dados
        if (bloodPressureData.getSystolicPressure() > 140 || bloodPressureData.getDiastolicPressure() > 90) {
            logger.warn("High blood pressure detected for patient {}: {}/{} mmHg - Classification: {}",
                    bloodPressureData.getPatientId(),
                    bloodPressureData.getSystolicPressure(),
                    bloodPressureData.getDiastolicPressure(),
                    bloodPressureData.getClassification());
        }

        if ("CRISE_HIPERTENSIVA".equals(bloodPressureData.getClassification())) {
            logger.error("HYPERTENSIVE CRISIS detected for patient {}: {}/{} mmHg - URGENT ATTENTION REQUIRED!",
                    bloodPressureData.getPatientId(),
                    bloodPressureData.getSystolicPressure(),
                    bloodPressureData.getDiastolicPressure());
        }
    }

    private String classifyBloodPressure(Integer systolicPressure, Integer diastolicPressure) {
        if (systolicPressure == null || diastolicPressure == null) {
            return "UNKNOWN";
        }

        if (systolicPressure >= 180 || diastolicPressure >= 120) {
            return "CRISE_HIPERTENSIVA";
        } else if (systolicPressure >= 160 || diastolicPressure >= 100) {
            return "HIPERTENSAO_ESTAGIO_2";
        } else if (systolicPressure >= 140 || diastolicPressure >= 90) {
            return "HIPERTENSAO_ESTAGIO_1";
        } else if (systolicPressure >= 120 || diastolicPressure >= 80) {
            return "PRE_HIPERTENSAO";
        } else {
            return "NORMAL";
        }
    }
}