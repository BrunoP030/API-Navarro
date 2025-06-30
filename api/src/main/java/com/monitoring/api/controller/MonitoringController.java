package com.monitoring.api.controller;

import com.monitoring.api.dto.MonitoringRequest;
import com.monitoring.api.model.MonitoringData;
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
    @PostMapping("/save")
    public ResponseEntity<MonitoringData> receiveMonitoringData(@Valid @RequestBody MonitoringRequest request) {
        try {
            MonitoringData savedData = monitoringService.saveMonitoringData(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedData);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Endpoint para consultar dados de HeartRate por paciente
    @GetMapping("/monitoring/{patientId}")
    public ResponseEntity<List<MonitoringData>> getMonitoringData(@PathVariable String patientId) {
        List<MonitoringData> data = monitoringService.getMonitoringByPatient(patientId);
        return ResponseEntity.ok(data);
    }

    // Health check
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Monitoring API is running!");
    }
}