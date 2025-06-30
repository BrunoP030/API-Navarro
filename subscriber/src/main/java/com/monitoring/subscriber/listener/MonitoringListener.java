package com.monitoring.subscriber.listener;

import com.monitoring.subscriber.config.RabbitMQConfig;
import com.monitoring.subscriber.model.MonitoringData;
import com.monitoring.subscriber.service.MonitoringAnalysisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MonitoringListener {

    private static final Logger logger = LoggerFactory.getLogger(MonitoringListener.class);

    @Autowired
    private MonitoringAnalysisService analysisService;

    @RabbitListener(queues = RabbitMQConfig.MONITORING_QUEUE)
    public void receiveMonitoringData(MonitoringData monitoringData) {

        try {
            logger.info("Mensagem recebida no MonitoringListener: {}", monitoringData);

            boolean apiCallSuccess = sendToMonitoringApi(monitoringData);
            // Determina o tipo de mensagem baseado no conteúdo

            if (apiCallSuccess) {
                logger.info("HeartRate data sent to API successfully for patient: {}", monitoringData.getPatientId());
            } else {
                logger.error("Failed to send HeartRate data to API for patient: {}", monitoringData.getPatientId());
                // Dependendo da sua estratégia, você pode re-lançar a exceção ou lidar de outra forma
                throw new RuntimeException("Failed to communicate with HeartRate API");
            }

            logger.info("HeartRate data processed successfully for patient: {}", monitoringData.getPatientId());

        } catch (Exception e) {
            logger.error("Erro ao processar mensagem de monitoring: {}", e.getMessage(), e);
        }
    }

    private boolean sendToMonitoringApi(MonitoringData monitoringData) {
        try {
            logger.info("Attempting to send HeartRate data to API for patient: {}", monitoringData.getPatientId());

            // Chama o serviço HTTP
            analysisService.sendMonitoringData(monitoringData);

            logger.debug("API call completed successfully for patient: {}", monitoringData.getPatientId());
            return true;

        } catch (Exception e) {
            logger.error("Failed to send HeartRate data to API for patient {}: {}",
                    monitoringData.getPatientId(), e.getMessage(), e);
            return false;
        }
    }
}