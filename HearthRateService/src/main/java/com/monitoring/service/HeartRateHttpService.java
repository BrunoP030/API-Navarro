package com.monitoring.service;

import com.monitoring.model.HeartRateData;
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
public class HeartRateHttpService {

    private static final Logger logger = LoggerFactory.getLogger(HeartRateHttpService.class);

    private final WebClient heartRateWebClient;
    private final WebClient monitoringWebClient;

    public HeartRateHttpService(@Value("${heartrate.api.url}") String heartRateApiUrl,
                                @Value("${monitoring.api.url}") String monitoringApiUrl) {
        this.heartRateWebClient = WebClient.builder()
                .baseUrl(heartRateApiUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(1024 * 1024))
                .build();

        this.monitoringWebClient = WebClient.builder()
                .baseUrl(monitoringApiUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(1024 * 1024))
                .build();
    }

    public void sendHeartRateData(HeartRateData heartRateData) {
        logger.info("Sending BloodPressure data for patient: {}", heartRateData.getPatientId());

        // Envia para API BloodPressure de forma síncrona (bloqueia até completar)
        sendHeartRateDataApi(heartRateData).block();

        // Envia para API Monitoring de forma síncrona (bloqueia até completar)
        sendToMonitoringApi(heartRateData).block();
    }

    public Mono<String> sendHeartRateDataApi(HeartRateData heartRateData) {
            return heartRateWebClient.post()
                .uri("/save")
                .body(Mono.just(heartRateData), HeartRateData.class)
                .retrieve()
                .onStatus(status -> status.isError(), response -> {
                    return response.bodyToMono(String.class)
                            .map(errorBody -> new RuntimeException("HeartRate Api Error: " + errorBody));
                })
                .bodyToMono(String.class)
                .timeout(Duration.ofSeconds(30))
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(2))
                        .filter(throwable -> !(throwable instanceof WebClientResponseException.BadRequest)))
                .doOnSuccess(response -> {
                    logger.info("Successfully sent HeartRate data to API for patient: {}",
                            heartRateData.getPatientId());
                })
                .doOnError(error -> {
                    logger.error("Error sending HeartRate data to API for patient {}: {}",
                            heartRateData.getPatientId(), error.getMessage());
                })
                .onErrorReturn("Failed to send to HeartRate API");

    }

    // Manda pro Monitoring
    private Mono<String> sendToMonitoringApi(HeartRateData heartRateData) {
        return monitoringWebClient.post()
                .uri("/save") // Certifique-se que a baseUrl já inclui /api/monitoring
                .body(Mono.just(heartRateData), HeartRateData.class)
                .retrieve()
                .onStatus(status -> status.isError(), response -> {
                    return response.bodyToMono(String.class)
                            .map(errorBody -> new RuntimeException("Monitoring API Error: " + errorBody));
                })
                .bodyToMono(String.class)
                .timeout(Duration.ofSeconds(30))
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(2))
                        .filter(throwable -> !(throwable instanceof WebClientResponseException.BadRequest)))
                .doOnSuccess(response -> {
                    logger.info("Successfully sent HeartRate data to Monitoring API for patient: {}",
                            heartRateData.getPatientId());
                })
                .doOnError(error -> {
                    logger.error("Error sending HeartRate data to Monitoring API for patient {}: {}",
                            heartRateData.getPatientId(), error.getMessage());
                })
                .onErrorReturn("Failed to send to Monitoring API");
    }
}
