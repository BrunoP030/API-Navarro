package com.monitoring.subscriber.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MonitoringData {

    @JsonProperty("patientId")
    private String patientId;

    @JsonProperty("heartRate")
    private Integer heartRate;

    @JsonProperty("systolicPressure")
    private Integer systolicPressure;

    @JsonProperty("diastolicPressure")
    private Integer diastolicPressure;

    @JsonProperty("riskLevel")
    private String riskLevel; // "NORMAL", "WARNING", "CRITICAL"


    public MonitoringData() {
    }

    public MonitoringData(String patientId) {
        this.patientId = patientId;
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

    public boolean isMediumRisk() {
        return "MEDIUM".equals(this.riskLevel);
    }

    public boolean isHighRisk() {
        return "HIGH".equals(this.riskLevel);
    }

    // toString para debugging
    @Override
    public String toString() {
        return "MonitoringData{" +
                "patientId='" + patientId + '\'' +
                ", heartRate=" + heartRate +
                ", systolicPressure=" + systolicPressure +
                ", diastolicPressure=" + diastolicPressure +
                ", riskLevel=" + riskLevel +
                '}';
    }
}