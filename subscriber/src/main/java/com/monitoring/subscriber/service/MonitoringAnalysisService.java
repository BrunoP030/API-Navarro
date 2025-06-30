package com.monitoring.subscriber.service;

import com.monitoring.subscriber.model.MonitoringData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

@Service
public class MonitoringAnalysisService {

    private static final Logger logger = LoggerFactory.getLogger(MonitoringAnalysisService.class);

    private WebClient webClient;

    public void MonitoringAnalysisService(@Value("${monitoring.api.url}") String monitoringApiUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(monitoringApiUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(1024 * 1024))
                .build();
    }

    public void sendMonitoringData(MonitoringData monitoringData) {
        logger.info("Sending Monitoring data for patient: {}", monitoringData.getPatientId());

        // Envia para API BloodPressure de forma síncrona (bloqueia até completar)
        sendMonitoringDataApi(monitoringData).block();

    }

    public Mono<String> sendMonitoringDataApi(MonitoringData monitoringData) {
        return webClient.post()
                .uri("/save")
                .body(Mono.just(monitoringData), MonitoringData.class)
                .retrieve()
                .onStatus(status -> status.isError(), response -> {
                    return response.bodyToMono(String.class)
                            .map(errorBody -> new RuntimeException("Monitoring Api Error: " + errorBody));
                })
                .bodyToMono(String.class)
                .timeout(Duration.ofSeconds(30))
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(2))
                        .filter(throwable -> !(throwable instanceof WebClientResponseException.BadRequest)))
                .doOnSuccess(response -> {
                    logger.info("Successfully sent Monitoring data to API for patient: {}",
                            monitoringData.getPatientId());
                })
                .doOnError(error -> {
                    logger.error("Error sending Monitoring data to API for patient {}: {}",
                            monitoringData.getPatientId(), error.getMessage());
                })
                .onErrorReturn("Failed to send to HeartRate API");

    }

    public MonitoringAnalysisService() {
        this.webClient = WebClient.builder().build();
    }

    private String analyzeHeartRate(Integer heartRate) {
        if (heartRate < 60 || heartRate > 100) {
            if (heartRate < 40 || heartRate > 150) {
                return "HIGH";
            }
            return "MEDIUM";
        }
        return "LOW";
    }

    private String analyzeBloodPressure(Integer systolicPressure, Integer diastolicPressure) {
        // Análise baseada em diretrizes médicas simplificadas
        if (systolicPressure >= 180 || diastolicPressure >= 120) {
            return "HIPERTENSICA"; // Crise hipertensiva
        } else if (systolicPressure >= 140 || diastolicPressure >= 90) {
            return "HIPERTENSAO"; // Hipertensão
        } else if (systolicPressure < 90 || diastolicPressure < 60) {
            return "HIPOTENSAO"; // Hipotensão
        }
        return "NORMAL";
    }
}