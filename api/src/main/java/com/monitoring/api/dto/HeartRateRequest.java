package com.monitoring.api.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class HeartRateRequest {

    @NotBlank(message = "Patient ID é obrigatório")
    private String patientId;

    @NotNull(message = "Heart rate é obrigatório")
    @Min(value = 30, message = "Heart rate deve ser maior que 30")
    @Max(value = 300, message = "Heart rate deve ser menor que 300")
    private Integer heartRate;

    private String deviceId;

    // Construtores
    public HeartRateRequest() {}

    public HeartRateRequest(String patientId, Integer heartRate, String deviceId) {
        this.patientId = patientId;
        this.heartRate = heartRate;
        this.deviceId = deviceId;
    }

    // Getters e Setters
    public String getPatientId() { return patientId; }
    public void setPatientId(String patientId) { this.patientId = patientId; }

    public Integer getHeartRate() { return heartRate; }
    public void setHeartRate(Integer heartRate) { this.heartRate = heartRate; }

    public String getDeviceId() { return deviceId; }
    public void setDeviceId(String deviceId) { this.deviceId = deviceId; }
}