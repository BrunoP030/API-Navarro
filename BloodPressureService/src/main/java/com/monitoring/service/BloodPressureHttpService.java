package com.monitoring.service;

import com.monitoring.model.BloodPressureData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class BloodPressureHttpService {

    private static final Logger logger = LoggerFactory.getLogger(BloodPressureHttpService.class);

    private final WebClient bloodPressureWebClient;
    private final WebClient monitoringWebClient;

    public BloodPressureHttpService(@Value("${bloodpressure.api.url}") String bloodPressureApiUrl,
                                    @Value("${monitoring.api.url}") String monitoringApiUrl) {
        this.bloodPressureWebClient = WebClient.builder()
                .baseUrl(bloodPressureApiUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        this.monitoringWebClient = WebClient.builder()
                .baseUrl(monitoringApiUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public void sendBloodPressureData(BloodPressureData bloodPressureData) {
        // Envia para API BloodPressure
        bloodPressureWebClient.post()
                .uri("/save")
                .body(Mono.just(bloodPressureData), BloodPressureData.class)
                .retrieve()
                .bodyToMono(String.class)
                .doOnSuccess(response -> {
                    logger.info("Successfully sent BloodPressure data to API: {}", response);
                })
                .doOnError(error -> {
                    logger.error("Error sending BloodPressure data to API: {}", error.getMessage());
                })
                .onErrorResume(throwable -> {
                    logger.warn("BloodPressure API call failed, continuing processing...");
                    return Mono.empty();
                })
                .subscribe();

        // Envia para API Monitoring
        monitoringWebClient.post()
                .uri("/save")
                .body(Mono.just(bloodPressureData), BloodPressureData.class)
                .retrieve()
                .bodyToMono(String.class)
                .doOnSuccess(response -> {
                    logger.info("Successfully sent BloodPressure data to Monitoring API: {}", response);
                })
                .doOnError(error -> {
                    logger.error("Error sending BloodPressure data to Monitoring API: {}", error.getMessage());
                })
                .onErrorResume(throwable -> {
                    logger.warn("Monitoring API call failed, continuing processing...");
                    return Mono.empty();
                })
                .subscribe();
    }
}