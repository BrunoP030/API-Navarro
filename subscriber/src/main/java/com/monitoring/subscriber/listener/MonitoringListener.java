package com.monitoring.subscriber.listener;

import com.monitoring.subscriber.dto.BloodPressureMessage;
import com.monitoring.subscriber.dto.HeartRateMessage;
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

    @RabbitListener(queues = "monitoring.analysis")
    public void receiveMonitoringData(Object message) {
        try {
            logger.info("Mensagem recebida no MonitoringListener: {}", message);

            // Determina o tipo de mensagem baseado no conteúdo
            if (message.toString().contains("heartRate")) {
                // É uma mensagem de HeartRate
                HeartRateMessage heartRateMsg = parseHeartRateMessage(message);
                if (heartRateMsg != null) {
                    analysisService.processHeartRateData(heartRateMsg);
                }
            } else if (message.toString().contains("systolic") && message.toString().contains("diastolic")) {
                // É uma mensagem de BloodPressure
                BloodPressureMessage bloodPressureMsg = parseBloodPressureMessage(message);
                if (bloodPressureMsg != null) {
                    analysisService.processBloodPressureData(bloodPressureMsg);
                }
            } else {
                logger.warn("Tipo de mensagem não reconhecido: {}", message);
            }

        } catch (Exception e) {
            logger.error("Erro ao processar mensagem de monitoring: {}", e.getMessage(), e);
        }
    }

    private HeartRateMessage parseHeartRateMessage(Object message) {
        try {
            // Implementação simplificada - em produção usaria Jackson ObjectMapper
            String msgStr = message.toString();
            // Aqui você faria o parsing adequado da mensagem JSON
            // Por simplicidade, estou criando um objeto de exemplo
            HeartRateMessage heartRateMsg = new HeartRateMessage();
            // Parse dos campos da mensagem...
            return heartRateMsg;
        } catch (Exception e) {
            logger.error("Erro ao fazer parse da mensagem HeartRate: {}", e.getMessage());
            return null;
        }
    }

    private BloodPressureMessage parseBloodPressureMessage(Object message) {
        try {
            // Implementação simplificada - em produção usaria Jackson ObjectMapper
            String msgStr = message.toString();
            // Aqui você faria o parsing adequado da mensagem JSON
            // Por simplicidade, estou criando um objeto de exemplo
            BloodPressureMessage bloodPressureMsg = new BloodPressureMessage();
            // Parse dos campos da mensagem...
            return bloodPressureMsg;
        } catch (Exception e) {
            logger.error("Erro ao fazer parse da mensagem BloodPressure: {}", e.getMessage());
            return null;
        }
    }
}