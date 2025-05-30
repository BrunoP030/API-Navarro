package com.monitoring.service;

import com.monitoring.model.HeartRateData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class HeartRateHttpService {

    private static final Logger logger = LoggerFactory.getLogger(HeartRateHttpService.class);

    private final WebClient webClient;

    public HeartRateHttpService(@Value("${heartrate.api.url}") String apiUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(apiUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public void sendHeartRateData(HeartRateData heartRateData) {
        webClient.post()
                .uri("/save")
                .body(Mono.just(heartRateData), HeartRateData.class)
                .retrieve()
                .bodyToMono(String.class)
                .doOnSuccess(response -> {
                    logger.info("Successfully sent HeartRate data to API: {}", response);
                })
                .doOnError(error -> {
                    logger.error("Error sending HeartRate data to API: {}", error.getMessage());
                })
                .onErrorResume(throwable -> {
                    logger.warn("API call failed, continuing processing...");
                    return Mono.empty();
                })
                .subscribe();
    }
}
