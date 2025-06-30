package com.monitoring.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "monitoring_data")
public class MonitoringData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "patient_id")
    private String patientId;

    @Min(30)
    @Max(300)
    @Column(name = "heart_rate")
    private Integer heartRate;

    @Min(50)
    @Max(300)
    @Column(name = "systolic_pressure")
    private Integer systolicPressure;

    @Min(30)
    @Max(200)
    @Column(name = "diastolic_pressure")
    private Integer diastolicPressure;

    @Pattern(regexp = "^(NORMAL|WARNING|CRITICAL)$")
    @Column(name = "risk_level")
    private String riskLevel;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date createdAt;

    // Construtores
    public MonitoringData() {
        this.createdAt = new java.util.Date();
    }

    public MonitoringData(String patientId) {
        this();
        this.patientId = patientId;
    }

    public MonitoringData(String patientId, Integer heartRate) {
        this(patientId);
        this.heartRate = heartRate;
    }

    public MonitoringData(String patientId, Integer systolicPressure, Integer diastolicPressure) {
        this(patientId);
        this.systolicPressure = systolicPressure;
        this.diastolicPressure = diastolicPressure;
    }

    public MonitoringData(String patientId, Integer heartRate, Integer systolicPressure,
                          Integer diastolicPressure, String riskLevel) {
        this(patientId);
        this.heartRate = heartRate;
        this.systolicPressure = systolicPressure;
        this.diastolicPressure = diastolicPressure;
        this.riskLevel = riskLevel;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public java.util.Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(java.util.Date createdAt) {
        this.createdAt = createdAt;
    }

    // Métodos auxiliares para verificação de risco
    public boolean isMediumRisk() {
        return "MEDIUM".equals(this.riskLevel);
    }

    public boolean isHighRisk() {
        return "HIGH".equals(this.riskLevel);
    }

    public boolean isCriticalRisk() {
        return "CRITICAL".equals(this.riskLevel);
    }

    public boolean isWarningRisk() {
        return "WARNING".equals(this.riskLevel);
    }

    public boolean isNormalRisk() {
        return "NORMAL".equals(this.riskLevel);
    }

    // Métodos auxiliares para verificação de dados
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

    // Métodos de persistência
    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = new java.util.Date();
        }
    }

    @Override
    public String toString() {
        return "MonitoringData{" +
                "id=" + id +
                ", patientId='" + patientId + '\'' +
                ", heartRate=" + heartRate +
                ", systolicPressure=" + systolicPressure +
                ", diastolicPressure=" + diastolicPressure +
                ", riskLevel='" + riskLevel + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}