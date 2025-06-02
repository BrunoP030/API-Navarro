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

            // Converte PatientData para BloodPressureData
            BloodPressureData bloodPressureData = convertToBloodPressureData(patientData);

            // Processa os dados localmente
            processBloodPressureData(bloodPressureData);

            // Envia os dados para a API BloodPressure
            bloodPressureHttpService.sendBloodPressureData(bloodPressureData);

            // Publica mensagem para o próximo serviço (Monitoring)
            monitoringPublisher.publishToMonitoring(patientData);

            logger.info("BloodPressure processing completed for patient: {}", patientData.getPatientId());

        } catch (Exception e) {
            logger.error("Error processing BloodPressure message for patient {}: {}",
                    patientData != null ? patientData.getPatientId() : "unknown",
                    e.getMessage(), e);
            throw e; // Re-throw para ativar retry mechanism
        }
    }

    private BloodPressureData convertToBloodPressureData(PatientData patientData) {
        BloodPressureData bloodPressureData = new BloodPressureData();
        bloodPressureData.setPatientId(patientData.getPatientId());
        bloodPressureData.setSystolicPressure(patientData.getSystolicPressure());
        bloodPressureData.setDiastolicPressure(patientData.getDiastolicPressure());
        bloodPressureData.setTimestamp(patientData.getTimestamp());

        // Classificação da pressão arterial
        bloodPressureData.setClassification(classifyBloodPressure(
                patientData.getSystolicPressure(),
                patientData.getDiastolicPressure()
        ));

        return bloodPressureData;
    }

    private void processBloodPressureData(BloodPressureData bloodPressureData) {
        // Validação e processamento dos dados
        if (bloodPressureData.getSystolicPressure() > 140 || bloodPressureData.getDiastolicPressure() > 90) {
            logger.warn("High blood pressure detected for patient {}: {}/{} mmHg",
                    bloodPressureData.getPatientId(),
                    bloodPressureData.getSystolicPressure(),
                    bloodPressureData.getDiastolicPressure());
        }

        if ("CRISE_HIPERTENSIVA".equals(bloodPressureData.getClassification())) {
            logger.error("HYPERTENSIVE CRISIS detected for patient {}: {}/{} mmHg - URGENT ATTENTION REQUIRED!",
                    bloodPressureData.getPatientId(),
                    bloodPressureData.getSystolicPressure(),
                    bloodPressureData.getDiastolicPressure());
        }
    }

    private String classifyBloodPressure(Integer systolic, Integer diastolic) {
        if (systolic >= 180 || diastolic >= 120) {
            return "CRISE_HIPERTENSIVA";
        } else if (systolic >= 160 || diastolic >= 100) {
            return "HIPERTENSAO_ESTAGIO_2";
        } else if (systolic >= 140 || diastolic >= 90) {
            return "HIPERTENSAO_ESTAGIO_1";
        } else if (systolic >= 120 || diastolic >= 80) {
            return "PRE_HIPERTENSAO";
        } else {
            return "NORMAL";
        }
    }
}