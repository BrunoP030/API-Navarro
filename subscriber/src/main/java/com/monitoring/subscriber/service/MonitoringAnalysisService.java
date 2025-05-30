package com.monitoring.subscriber.service;

import com.monitoring.subscriber.dto.BloodPressureMessage;
import com.monitoring.subscriber.dto.HeartRateMessage;
import com.monitoring.subscriber.dto.MonitoringAnalysisRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class MonitoringAnalysisService {

    private static final Logger logger = LoggerFactory.getLogger(MonitoringAnalysisService.class);

    private final WebClient webClient;

    @Value("${monitoring.api.url:http://localhost:8081}")
    private String monitoringApiUrl;

    public MonitoringAnalysisService() {
        this.webClient = WebClient.builder().build();
    }

    public void processHeartRateData(HeartRateMessage message) {
        logger.info("Processando dados de HeartRate: {}", message);

        // Análise simples dos dados de batimento cardíaco
        String alertLevel = analyzeHeartRate(message.getHeartRate());

        // Cria request para API de análise
        MonitoringAnalysisRequest request = new MonitoringAnalysisRequest(
                message.getPatientId(),
                message.getHeartRate(),
                message.getTimestamp(),
                message.getDeviceId(),
                alertLevel
        );

        // Envia para API de análise
        sendToAnalysisApi(request);
    }

    public void processBloodPressureData(BloodPressureMessage message) {
        logger.info("Processando dados de BloodPressure: {}", message);

        // Análise simples dos dados de pressão arterial
        String alertLevel = analyzeBloodPressure(message.getSystolic(), message.getDiastolic());

        // Cria request para API de análise
        MonitoringAnalysisRequest request = new MonitoringAnalysisRequest(
                message.getPatientId(),
                message.getSystolic(),
                message.getDiastolic(),
                message.getTimestamp(),
                message.getDeviceId(),
                alertLevel
        );

        // Envia para API de análise
        sendToAnalysisApi(request);
    }

    private String analyzeHeartRate(Integer heartRate) {
        if (heartRate < 60 || heartRate > 100) {
            if (heartRate < 40 || heartRate > 150) {
                return "CRITICAL";
            }
            return "WARNING";
        }
        return "NORMAL";
    }

    private String analyzeBloodPressure(Integer systolic, Integer diastolic) {
        // Análise baseada em diretrizes médicas simplificadas
        if (systolic >= 180 || diastolic >= 120) {
            return "CRITICAL"; // Crise hipertensiva
        } else if (systolic >= 140 || diastolic >= 90) {
            return "WARNING"; // Hipertensão
        } else if (systolic < 90 || diastolic < 60) {
            return "WARNING"; // Hipotensão
        }
        return "NORMAL";
    }

    private void sendToAnalysisApi(MonitoringAnalysisRequest request) {
        String endpoint = monitoringApiUrl + "/api/analysis/monitoring";

        webClient.post()
                .uri(endpoint)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(String.class)
                .doOnSuccess(response ->
                        logger.info("Dados enviados com sucesso para API de análise: {}", response))
                .doOnError(error ->
                        logger.error("Erro ao enviar dados para API de análise: {}", error.getMessage()))
                .onErrorResume(error -> {
                    logger.warn("Falha na comunicação com API de análise, dados serão processados localmente");
                    return Mono.empty();
                })
                .subscribe();
    }
}