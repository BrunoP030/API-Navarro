package com.monitoring.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;


public class PatientData {

    @NotBlank(message = "O ID do paciente é obrigatório")
    private String patientId;

    @NotBlank(message = "O nome do paciente é obrigatório")
    private String patientName;

    @NotNull(message = "A frequência cardíaca é necessária")
    @Positive(message = "A frequência cardíaca deve ser positiva")
    private Integer heartRate;

    @NotNull(message = "A pressão sistólica é necessária")
    @Positive(message = "A pressão sistólica deve ser positiva")
    private Integer systolicPressure;

    @NotNull(message = "A pressão diastólica é necessária")
    @Positive(message = "A pressão diastólica deve ser positiva")
    private Integer diastolicPressure;


    public PatientData(String patientId, String patientName, Integer heartRate,
                       Integer systolicPressure, Integer diastolicPressure) {
        this.patientId = patientId;
        this.patientName = patientName;
        this.heartRate = heartRate;
        this.systolicPressure = systolicPressure;
        this.diastolicPressure = diastolicPressure;
    }

    // Getters and Setters
    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
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


    @Override
    public String toString() {
        return "PatientData{" +
                "pacienteId='" + patientId + '\'' +
                ", pacienteNome='" + patientName + '\'' +
                ", frequenciaCardiaca=" + heartRate +
                ", pressaoSistolica=" + systolicPressure +
                ", pressaoDiastolica=" + diastolicPressure +
                '}';
    }
}