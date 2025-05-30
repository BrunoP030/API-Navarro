package com.monitoring.subscriber.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

public class MonitoringAnalysisRequest {

    @JsonProperty("patientId")
    private String patientId;

    @JsonProperty("dataType")
    private String dataType; // "HEART_RATE" ou "BLOOD_PRESSURE"

    @JsonProperty("heartRate")
    private Integer heartRate;

    @JsonProperty("systolic")
    private Integer systolic;

    @JsonProperty("diastolic")
    private Integer diastolic;

    @JsonProperty("timestamp")
    private LocalDateTime timestamp;

    @JsonProperty("deviceId")
    private String deviceId;

    @JsonProperty("alertLevel")
    private String alertLevel; // "NORMAL", "WARNING", "CRITICAL"

    // Construtores
    public MonitoringAnalysisRequest() {}

    // Construtor para HeartRate
    public MonitoringAnalysisRequest(String patientId, Integer heartRate, LocalDateTime timestamp,
                                     String deviceId, String alertLevel) {
        this.patientId = patientId;
        this.dataType = "HEART_RATE";
        this.heartRate = heartRate;
        this.timestamp = timestamp;
        this.deviceId = deviceId;
        this.alertLevel = alertLevel;
    }

    // Construtor para BloodPressure
    public MonitoringAnalysisRequest(String patientId, Integer systolic, Integer diastolic,
                                     LocalDateTime timestamp, String deviceId, String alertLevel) {
        this.patientId = patientId;
        this.dataType = "BLOOD_PRESSURE";
        this.systolic = systolic;
        this.diastolic = diastolic;
        this.timestamp = timestamp;
        this.deviceId = deviceId;
        this.alertLevel = alertLevel;
    }

    // Getters e Setters
    public String getPatientId() { return patientId; }
    public void setPatientId(String patientId) { this.patientId = patientId; }

    public String getDataType() { return dataType; }
    public void setDataType(String dataType) { this.dataType = dataType; }

    public Integer getHeartRate() { return heartRate; }
    public void setHeartRate(Integer heartRate) { this.heartRate = heartRate; }

    public Integer getSystolic() { return systolic; }
    public void setSystolic(Integer systolic) { this.systolic = systolic; }

    public Integer getDiastolic() { return diastolic; }
    public void setDiastolic(Integer diastolic) { this.diastolic = diastolic; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public String getDeviceId() { return deviceId; }
    public void setDeviceId(String deviceId) { this.deviceId = deviceId; }

    public String getAlertLevel() { return alertLevel; }
    public void setAlertLevel(String alertLevel) { this.alertLevel = alertLevel; }
}
