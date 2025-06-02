package com.monitoring.api.controller;

import com.monitoring.api.dto.BloodPressureRequest;
import com.monitoring.api.dto.HeartRateRequest;
import com.monitoring.api.model.BloodPressureData;
import com.monitoring.api.model.HeartRateData;
import com.monitoring.api.service.MonitoringService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/monitoring")
@CrossOrigin(origins = "*")
public class MonitoringController {

    @Autowired
    private MonitoringService monitoringService;

    // Endpoint para receber dados de HeartRate do subscriber
    @PostMapping("/heartrate")
    public ResponseEntity<HeartRateData> receiveHeartRateData(@Valid @RequestBody HeartRateRequest request) {
        try {
            HeartRateData savedData = monitoringService.saveHeartRateData(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedData);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Endpoint para receber dados de BloodPressure do subscriber
    @PostMapping("/bloodpressure")
    public ResponseEntity<BloodPressureData> receiveBloodPressureData(@Valid @RequestBody BloodPressureRequest request) {
        try {
            BloodPressureData savedData = monitoringService.saveBloodPressureData(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedData);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Endpoint para consultar dados de HeartRate por paciente
    @GetMapping("/heartrate/{patientId}")
    public ResponseEntity<List<HeartRateData>> getHeartRateData(@PathVariable String patientId) {
        List<HeartRateData> data = monitoringService.getHeartRateByPatient(patientId);
        return ResponseEntity.ok(data);
    }

    // Endpoint para consultar dados de BloodPressure por paciente
    @GetMapping("/bloodpressure/{patientId}")
    public ResponseEntity<List<BloodPressureData>> getBloodPressureData(@PathVariable String patientId) {
        List<BloodPressureData> data = monitoringService.getBloodPressureByPatient(patientId);
        return ResponseEntity.ok(data);
    }

    // Health check
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Monitoring API is running!");
    }
}
