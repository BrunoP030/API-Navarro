package com.monitoring.api.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class BloodPressureRequest {

    @NotBlank(message = "Patient ID é obrigatório")
    private String patientId;

    @NotNull(message = "Systolic é obrigatório")
    @Min(value = 50, message = "Systolic deve ser maior que 50")
    @Max(value = 300, message = "Systolic deve ser menor que 300")
    private Integer systolic;

    @NotNull(message = "Diastolic é obrigatório")
    @Min(value = 30, message = "Diastolic deve ser maior que 30")
    @Max(value = 200, message = "Diastolic deve ser menor que 200")
    private Integer diastolic;

    private String deviceId;

    // Construtores
    public BloodPressureRequest() {}

    public BloodPressureRequest(String patientId, Integer systolic, Integer diastolic, String deviceId) {
        this.patientId = patientId;
        this.systolic = systolic;
        this.diastolic = diastolic;
        this.deviceId = deviceId;
    }

    // Getters e Setters
    public String getPatientId() { return patientId; }
    public void setPatientId(String patientId) { this.patientId = patientId; }

    public Integer getSystolic() { return systolic; }
    public void setSystolic(Integer systolic) { this.systolic = systolic; }

    public Integer getDiastolic() { return diastolic; }
    public void setDiastolic(Integer diastolic) { this.diastolic = diastolic; }

    public String getDeviceId() { return deviceId; }
    public void setDeviceId(String deviceId) { this.deviceId = deviceId; }
}