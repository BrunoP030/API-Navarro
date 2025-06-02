package com.monitoring.subscriber.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BloodPressureMessage {

    @JsonProperty("patientId")
    private String patientId;

    @JsonProperty("systolic")
    private Integer systolic;

    @JsonProperty("diastolic")
    private Integer diastolic;

    @JsonProperty("deviceId")
    private String deviceId;

    // Construtores
    public BloodPressureMessage() {}

    public BloodPressureMessage(String patientId, Integer systolic, Integer diastolic,
                                String deviceId) {
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

    @Override
    public String toString() {
        return "BloodPressureMessage{" +
                "patientId='" + patientId + '\'' +
                ", systolic=" + systolic +
                ", diastolic=" + diastolic +
                ", deviceId='" + deviceId + '\'' +
                '}';
    }
}