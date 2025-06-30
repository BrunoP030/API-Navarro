package com.monitoring.api.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class MonitoringRequest {

    @NotBlank(message = "Patient ID é obrigatório")
    private String patientId;

//    @Min(value = , message = "Heart rate deve ser maior que 30")
//    @Max(value = 300, message = "Heart rate deve ser menor que 300")
    private Integer heartRate;

    @Min(value = 50, message = "Pressão sistólica deve ser maior que 50")
    @Max(value = 300, message = "Pressão sistólica deve ser menor que 300")
    private Integer systolicPressure;

    @Min(value = 30, message = "Pressão diastólica deve ser maior que 30")
    @Max(value = 200, message = "Pressão diastólica deve ser menor que 200")
    private Integer diastolicPressure;

//    @Pattern(regexp = "^(NORMAL|WARNING|CRITICAL)$",
//            message = "Risk level deve ser NORMAL, WARNING ou CRITICAL")
    private String riskLevel;

    // Construtores
    public MonitoringRequest() {}

    public MonitoringRequest(String patientId) {
        this.patientId = patientId;
    }

    public MonitoringRequest(String patientId, Integer heartRate) {
        this.patientId = patientId;
        this.heartRate = heartRate;
    }

    public MonitoringRequest(String patientId, Integer systolicPressure, Integer diastolicPressure) {
        this.patientId = patientId;
        this.systolicPressure = systolicPressure;
        this.diastolicPressure = diastolicPressure;
    }

    public MonitoringRequest(String patientId, Integer heartRate, Integer systolicPressure,
                             Integer diastolicPressure, String riskLevel) {
        this.patientId = patientId;
        this.heartRate = heartRate;
        this.systolicPressure = systolicPressure;
        this.diastolicPressure = diastolicPressure;
        this.riskLevel = riskLevel;
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

    public Integer getSystolicPressure() {
        return systolicPressure;
    }

    public void setSystolicPressure(Integer systolicPressure) {
        this.systolicPressure = systolicPressure;
    }

    public Integer getDiastolicPressure() {
        return diastolicPressure;
    }

    public void setDiastolicPressure(Integer diastolicPressure) {
        this.diastolicPressure = diastolicPressure;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    // Métodos auxiliares para validação de negócio
    public boolean hasHeartRateData() {
        return heartRate != null;
    }

    public boolean hasBloodPressureData() {
        return systolicPressure != null && diastolicPressure != null;
    }

    public boolean hasCompleteData() {
        return hasHeartRateData() && hasBloodPressureData();
    }

    public boolean isValidBloodPressureRange() {
        if (!hasBloodPressureData()) return true;
        return systolicPressure > diastolicPressure;
    }

    // toString para debugging
    @Override
    public String toString() {
        return "MonitoringRequest{" +
                "patientId='" + patientId + '\'' +
                ", heartRate=" + heartRate +
                ", systolicPressure=" + systolicPressure +
                ", diastolicPressure=" + diastolicPressure +
                ", riskLevel='" + riskLevel + '\'' +
                '}';
    }
}