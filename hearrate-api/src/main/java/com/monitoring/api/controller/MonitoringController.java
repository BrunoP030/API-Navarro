package com.monitoring.api.controller;

import com.monitoring.api.dto.HeartRateRequest;
import com.monitoring.api.model.HeartRateData;
import com.monitoring.api.service.MonitoringService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/heartrate")
@CrossOrigin(origins = "*")
public class MonitoringController {

    private static final Logger logger = LoggerFactory.getLogger(MonitoringController.class);

    @Autowired
    private MonitoringService monitoringService;

    // Endpoint principal para salvar dados (mantendo seu padrão atual)
    @PostMapping("/save")
    public ResponseEntity<HeartRateData> receiveHeartRateData(@Valid @RequestBody HeartRateRequest request) {
        try {
            logger.info("Received HeartRate request: {}", request);

            HeartRateData savedData = monitoringService.saveHeartRateData(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedData);

        } catch (Exception e) {
            logger.error("Error saving HeartRate data: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Buscar dados por paciente (mantendo seu endpoint atual)
    @GetMapping("/heartrate/{patientId}")
    public ResponseEntity<List<HeartRateData>> getHeartRateByPatient(@PathVariable String patientId) {
        try {
            logger.info("Fetching HeartRate data for patient: {}", patientId);
            List<HeartRateData> heartRates = monitoringService.getHeartRateByPatient(patientId);

            if (heartRates.isEmpty()) {
                logger.info("No HeartRate data found for patient: {}", patientId);
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(heartRates);
        } catch (Exception e) {
            logger.error("Error fetching HeartRate data for patient {}: {}", patientId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Health check (mantendo seu endpoint)
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        logger.info("Health check requested");
        return ResponseEntity.ok("Monitoring API is running!");
    }

    // Métodos de validação
    private boolean isValidHeartRateData(HeartRateData heartRateData) {
        return heartRateData != null &&
                heartRateData.getPatientId() != null &&
                !heartRateData.getPatientId().trim().isEmpty() &&
                heartRateData.getHeartRate() != null &&
                heartRateData.getHeartRate() > 0;
    }
}