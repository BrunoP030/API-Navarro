package com.monitoring.controller;

import com.monitoring.model.PatientData;
import com.monitoring.service.PatientPublisherService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/patients")
@CrossOrigin(origins = "*")
public class PatientController {

    @Autowired
    private PatientPublisherService patientPublisherService;

    @PostMapping("/publish")
    public ResponseEntity<String> publishPatientData(@Valid @RequestBody PatientData patientData) {
        try {
            patientPublisherService.publishPatientData(patientData);
            return ResponseEntity.ok("Dados do paciente publicados com sucesso");
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Erro ao publicar dados do paciente: " + e.getMessage());
        }
    }

    @PostMapping("/publish/random")
    public ResponseEntity<String> publishRandomPatientData() {
        try {
            patientPublisherService.generateAndPublishPatientData();
            return ResponseEntity.ok("Dados aleatórios de pacientes publicados com sucesso");
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Erro ao publicar dados aleatórios do paciente: " + e.getMessage());
        }
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("O Serviço de Atendimento ao Paciente está em execução");
    }
}