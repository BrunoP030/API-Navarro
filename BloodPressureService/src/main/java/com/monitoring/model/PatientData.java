package com.monitoring.model;

public class PatientData {

    private String patientId;
    private String patientName;
    private Integer heartRate;
    private Integer systolicPressure;
    private Integer diastolicPressure;
    private String riskLevel;

    // Constructors
    public PatientData() {
    }

    public PatientData(String patientId, String patientName) {
        this.patientId = patientId;
        this.patientName = patientName;
    }

    public PatientData(String patientId, String patientName, Integer heartRate,
                       Integer systolicPressure, Integer diastolicPressure, String riskLevel) {
        this.patientId = patientId;
        this.patientName = patientName;
        this.heartRate = heartRate;
        this.systolicPressure = systolicPressure;
        this.diastolicPressure = diastolicPressure;
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

    @Override
    public String toString() {
        return "PatientData{" +
                "patientId='" + patientId + '\'' +
                ", patientName='" + patientName + '\'' +
                ", heartRate=" + heartRate +
                ", systolicPressure=" + systolicPressure +
                ", diastolicPressure=" + diastolicPressure +
                ", riskLevel='" + riskLevel + '\'' +
                '}';
    }
}