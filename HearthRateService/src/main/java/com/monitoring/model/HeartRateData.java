package com.monitoring.model;


public class HeartRateData {
    private String patientId;
    private String patientName;
    private Integer heartRate;
    private String riskLevel;


    public HeartRateData() {}

    public HeartRateData(String patientId, String patientName, Integer heartRate,
                       String riskLevel) {
        this.patientId = patientId;
        this.patientName = patientName;
        this.heartRate = heartRate;
        this.riskLevel = riskLevel;
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

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    @Override
    public String toString() {
        return "HeartRateData{" +
                "patientId='" + patientId + '\'' +
                ", patientName='" + patientName + '\'' +
                ", heartRate=" + heartRate +
                ", riskLevel='" + riskLevel + '\'' +
                '}';
    }
}