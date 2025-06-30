package com.monitoring.api.dto;

import jakarta.validation.constraints.*;

public class HeartRateRequest {

    @NotBlank(message = "Patient ID é obrigatório")
    private String patientId;

    @NotNull(message = "Heart rate é obrigatório")
    @Min(value = 30, message = "Heart rate deve ser maior que 30 bpm")
    @Max(value = 300, message = "Heart rate deve ser menor que 300 bpm")
    private Integer heartRate;

    @NotBlank(message = "Nome do paciente é obrigatório")
    @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
    private String patientName;

    @Pattern(regexp = "^(LOW|MEDIUM|HIGH)$", message = "Risk level deve ser: LOW, MEDIUM ou HIGH")
    private String riskLevel;

    // Construtores
    public HeartRateRequest() {
        this.riskLevel = "LOW";
    }

    public HeartRateRequest(String patientId, Integer heartRate, String patientName) {
        this.patientId = patientId;
        this.heartRate = heartRate;
        this.patientName = patientName;
        this.riskLevel = calculateRiskLevel(heartRate);
    }

    public HeartRateRequest(String patientId, Integer heartRate, String patientName,
                             String riskLevel) {
        this.patientId = patientId;
        this.heartRate = heartRate;
        this.patientName = patientName;
        this.riskLevel = riskLevel != null ? riskLevel : calculateRiskLevel(heartRate);
    }

    // Getters e Setters
    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public Integer getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(Integer heartRate) {
        this.heartRate = heartRate;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    // Método para calcular o nível de risco baseado na frequência cardíaca
    private String calculateRiskLevel(Integer heartRate) {
        if (heartRate == null) return "LOW";

        if (heartRate < 50 || heartRate > 120) return "HIGH";
        else if (heartRate < 60 || heartRate > 100) return "MEDIUM";
        else return "LOW";
    }

}