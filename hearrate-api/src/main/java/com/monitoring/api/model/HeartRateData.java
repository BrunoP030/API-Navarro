package com.monitoring.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "heart_rate_data")
public class HeartRateData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "patient_id")
    private String patientId;

    @NotNull
    @Min(30)
    @Max(300)
    @Column(name = "heart_rate")
    private Integer heartRate;

    @Column(name = "risk_level")
    private String riskLevel;

    // Construtores
    public HeartRateData() {}

    public HeartRateData(String patientId, Integer heartRate) {
        this.patientId = patientId;
        this.heartRate = heartRate;
        this.riskLevel = riskLevel;
    }

    public HeartRateData(String patientId, Integer heartRate, String riskLevel) {
        this.patientId = patientId;
        this.heartRate = heartRate;
        this.riskLevel = riskLevel;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPatientId() { return patientId; }
    public void setPatientId(String patientId) { this.patientId = patientId; }

    public Integer getHeartRate() { return heartRate; }
    public void setHeartRate(Integer heartRate) { this.heartRate = heartRate; }

    public String getRiskLevel() { return riskLevel; }
    public void setRiskLevel(String riskLevel) { this.riskLevel = riskLevel; }
}