package com.monitoring.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public class BloodPressureData {
    private String patientId;
    private Integer systolicPressure;
    private Integer diastolicPressure;
    private String classification;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;

    // Constructors
    public BloodPressureData() {
        this.timestamp = LocalDateTime.now();
    }

    public BloodPressureData(String patientId, Integer systolicPressure, Integer diastolicPressure) {
        this();
        this.patientId = patientId;
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

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "BloodPressureData{" +
                "patientId='" + patientId + '\'' +
                ", systolicPressure=" + systolicPressure +
                ", diastolicPressure=" + diastolicPressure +
                ", classification='" + classification + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}