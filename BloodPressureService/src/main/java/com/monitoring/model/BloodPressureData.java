package com.monitoring.model;


public class BloodPressureData {
    private String patientId;
    private Integer systolicPressure;
    private Integer diastolicPressure;
    private String classification;

    // Constructors

    public BloodPressureData(String patientId, Integer systolicPressure, Integer diastolicPressure) {
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

    @Override
    public String toString() {
        return "BloodPressureData{" +
                "patientId='" + patientId + '\'' +
                ", systolicPressure=" + systolicPressure +
                ", diastolicPressure=" + diastolicPressure +
                ", classification='" + classification + '\'' +
                '}';
    }
}