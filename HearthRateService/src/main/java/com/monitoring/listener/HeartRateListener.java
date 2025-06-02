package com.monitoring.listener;

import com.monitoring.model.HeartRateData;
import com.monitoring.service.HeartRateHttpService;
import com.monitoring.service.BloodPressurePublisherService;
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

    @RabbitListener(queues = "heartrate.queue")
    public void receiveHeartRateData(HeartRateData heartRateData) {
        try {
            logger.info("Received HeartRate data: {}", heartRateData);

            // Envia os dados para a API de Heart Rate
            heartRateHttpService.sendHeartRateData(heartRateData);

            // Publica mensagem para o próximo serviço (BloodPressure)
            bloodPressurePublisherService.publishToBloodPressure(heartRateData);

            logger.info("HeartRate data processed successfully for patient: {}", heartRateData.getPatientId());

        } catch (Exception e) {
            logger.error("Error processing HeartRate data for patient {}: {}",
                    heartRateData.getPatientId(), e.getMessage(), e);
            throw e; // Relança a exceção para que o RabbitMQ possa reprocessar se necessário
        }
    }
}