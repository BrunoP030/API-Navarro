package com.monitoring.listener;

import com.monitoring.config.RabbitConfig;
import com.monitoring.model.HeartRateData;
import com.monitoring.model.PatientData;
import com.monitoring.service.BloodPressurePublisherService;
import com.monitoring.service.HeartRateHttpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HeartRateListener {

    private static final Logger logger = LoggerFactory.getLogger(HeartRateListener.class);

    @Autowired
    private HeartRateHttpService heartRateHttpService;

    @Autowired
    private BloodPressurePublisherService bloodPressurePublisherService;

    @RabbitListener(queues = RabbitConfig.HEARTRATE_QUEUE)
    public void receiveHeartRateData(PatientData patientData) {
        try {
            logger.info("Received HeartRate data: {}", patientData);

            if (!hasValidHeartRateData(patientData)) {
                logger.warn("Patient {} does not have valid heart rate data. Skipping processing.",
                        patientData.getPatientId());


                bloodPressurePublisherService.publishToBloodPressure(patientData);
                return;
            }

            HeartRateData heartRateData = convertToHeartRateData(patientData);

            // Classifica o risco baseado na frequência cardíaca
            classifyHeartRateRisk(heartRateData);

            // Processa os dados de frequência cardíaca
            processHeartRateData(heartRateData);

            PatientData patientData1 = convertToPatientData(patientData);

            bloodPressurePublisherService.publishToBloodPressure(patientData1);

            // Envia os dados para a API de Heart Rate
            boolean apiCallSuccess = sendToHeartRateApi(heartRateData);



            if (apiCallSuccess) {
                logger.info("HeartRate data sent to API successfully for patient: {}", patientData.getPatientId());
            } else {
                logger.error("Failed to send HeartRate data to API for patient: {}", patientData.getPatientId());
                // Dependendo da sua estratégia, você pode re-lançar a exceção ou lidar de outra forma
                throw new RuntimeException("Failed to communicate with HeartRate API");
            }

            logger.info("HeartRate data processed successfully for patient: {}", patientData.getPatientId());

        } catch (Exception e) {
            logger.error("Error processing HeartRate data for patient {}: {}",
                    patientData.getPatientId(), e.getMessage(), e);
            throw e;
        }
    }

    private HeartRateData convertToHeartRateData(PatientData patientData) {


        HeartRateData hearRateData = new HeartRateData(
                patientData.getPatientId(),
                patientData.getPatientName(),
                patientData.getHeartRate(),
                patientData.getRiskLevel()
        );

        return hearRateData;
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

    private boolean hasValidHeartRateData(PatientData patientData) {
        return patientData != null &&
                patientData.getHeartRate() != null &&
                patientData.getPatientId() != null;
    }

    private void classifyHeartRateRisk(HeartRateData heartRateData) {
        Integer heartRate = heartRateData.getHeartRate();
        String riskLevel = classifyHeartRate(heartRate);
        heartRateData.setRiskLevel(riskLevel);

        logger.debug("Heart rate {} classified as: {}", heartRate, riskLevel);
    }

    private void processHeartRateData(HeartRateData heartRateData) {
        Integer heartRate = heartRateData.getHeartRate();
        String riskLevel = heartRateData.getRiskLevel();

        // Validação e processamento dos dados de frequência cardíaca
        if ("HIGH".equals(riskLevel)) {
            logger.warn("High risk heart rate detected for patient {}: {} bpm - Risk Level: {}",
                    heartRateData.getPatientId(),
                    heartRate,
                    riskLevel);
        } else if ("MEDIUM".equals(riskLevel)) {
            logger.info("Medium risk heart rate detected for patient {}: {} bpm - Risk Level: {}",
                    heartRateData.getPatientId(),
                    heartRate,
                    riskLevel);
        } else {
            logger.info("Normal heart rate for patient {}: {} bpm - Risk Level: {}",
                    heartRateData.getPatientId(),
                    heartRate,
                    riskLevel);
        }
    }

    private String classifyHeartRate(Integer heartRate) {
        if (heartRate == null) {
            return "LOW";
        }

        if (heartRate < 50 || heartRate > 120) {
            return "HIGH";
        } else if (heartRate < 60 || heartRate > 100) {
            return "MEDIUM";
        } else {
            return "LOW";
        }
    }

    private boolean sendToHeartRateApi(HeartRateData heartRateData) {
        try {
            logger.info("Attempting to send HeartRate data to API for patient: {}", heartRateData.getPatientId());

            // Chama o serviço HTTP
            heartRateHttpService.sendHeartRateData(heartRateData);

            logger.debug("API call completed successfully for patient: {}", heartRateData.getPatientId());
            return true;

        } catch (Exception e) {
            logger.error("Failed to send HeartRate data to API for patient {}: {}",
                    heartRateData.getPatientId(), e.getMessage(), e);
            return false;
        }
    }
}