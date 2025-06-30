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
    private Integer systolicPressure;

    @NotNull(message = "Diastolic é obrigatório")
    @Min(value = 30, message = "Diastolic deve ser maior que 30")
    @Max(value = 200, message = "Diastolic deve ser menor que 200")
    private Integer diastolicPressure;


    // Construtores
    public BloodPressureRequest() {}

    public BloodPressureRequest(String patientId, Integer systolicPressure, Integer diastolicPressure) {
        this.patientId = patientId;
        this.systolicPressure = systolicPressure;
        this.diastolicPressure = diastolicPressure;
    }

    // Getters e Setters
    public String getPatientId() { return patientId; }
    public void setPatientId(String patientId) { this.patientId = patientId; }

    public Integer getSystolicPressure() { return systolicPressure; }
    public void setSystolicPressure(Integer systolicPressure) { this.systolicPressure = systolicPressure; }

    public Integer getDiastolicPressure() { return diastolicPressure; }
    public void setDiastolicPressure(Integer diastolicPressure) { this.diastolicPressure = diastolicPressure; }
}