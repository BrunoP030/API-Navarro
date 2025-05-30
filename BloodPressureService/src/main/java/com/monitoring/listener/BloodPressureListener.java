package com.monitoring.listener;

import com.monitoring.config.RabbitConfig;
import com.monitoring.model.BloodPressureData;
import com.monitoring.service.BloodPressureHttpService;
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

    @RabbitListener(queues = RabbitConfig.BLOODPRESSURE_QUEUE)
    public void handleBloodPressureMessage(BloodPressureData bloodPressureData) {
        try {
            logger.info("Received BloodPressure message: {}", bloodPressureData);

            // Processa os dados localmente (se necessário)
            processBloodPressureData(bloodPressureData);

            // Envia os dados para as APIs
            bloodPressureHttpService.sendBloodPressureData(bloodPressureData);

        } catch (Exception e) {
            logger.error("Error processing BloodPressure message: {}", e.getMessage(), e);
        }
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
}