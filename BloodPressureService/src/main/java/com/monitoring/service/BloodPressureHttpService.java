package com.monitoring.service;

import com.monitoring.model.BloodPressureData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

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
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(1024 * 1024))
                .build();

        this.monitoringWebClient = WebClient.builder()
                .baseUrl(monitoringApiUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(1024 * 1024))
                .build();
    }

    public void sendBloodPressureData(BloodPressureData bloodPressureData) {
        logger.info("Sending BloodPressure data for patient: {}", bloodPressureData.getPatientId());

        // Envia para API BloodPressure de forma síncrona (bloqueia até completar)
        sendToBloodPressureApi(bloodPressureData).block();

        // Envia para API Monitoring de forma síncrona (bloqueia até completar)
        sendToMonitoringApi(bloodPressureData).block();
    }

    private Mono<String> sendToBloodPressureApi(BloodPressureData bloodPressureData) {
        return bloodPressureWebClient.post()
                .uri("/save")
                .body(Mono.just(bloodPressureData), BloodPressureData.class)
                .retrieve()
                .onStatus(status -> status.isError(), response -> {
                    return response.bodyToMono(String.class)
                            .map(errorBody -> new RuntimeException("API Error: " + errorBody));
                })
                .bodyToMono(String.class)
                .timeout(Duration.ofSeconds(30))
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(2))
                        .filter(throwable -> !(throwable instanceof WebClientResponseException.BadRequest)))
                .doOnSuccess(response -> {
                    logger.info("Successfully sent BloodPressure data to API for patient: {}",
                            bloodPressureData.getPatientId());
                })
                .doOnError(error -> {
                    logger.error("Error sending BloodPressure data to API for patient {}: {}",
                            bloodPressureData.getPatientId(), error.getMessage());
                })
                .onErrorReturn("Failed to send to BloodPressure API");
    }

    private Mono<String> sendToMonitoringApi(BloodPressureData bloodPressureData) {
        return monitoringWebClient.post()
                .uri("/save")
                .body(Mono.just(bloodPressureData), BloodPressureData.class)
                .retrieve()
                .onStatus(status -> status.isError(), response -> {
                    return response.bodyToMono(String.class)
                            .map(errorBody -> new RuntimeException("API Error: " + errorBody));
                })
                .bodyToMono(String.class)
                .timeout(Duration.ofSeconds(30))
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(2))
                        .filter(throwable -> !(throwable instanceof WebClientResponseException.BadRequest)))
                .doOnSuccess(response -> {
                    logger.info("Successfully sent BloodPressure data to Monitoring API for patient: {}",
                            bloodPressureData.getPatientId());
                })
                .doOnError(error -> {
                    logger.error("Error sending BloodPressure data to Monitoring API for patient {}: {}",
                            bloodPressureData.getPatientId(), error.getMessage());
                })
                .onErrorReturn("Failed to send to Monitoring API");
    }
}