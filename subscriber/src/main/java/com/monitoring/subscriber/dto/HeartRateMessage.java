package com.monitoring.subscriber.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

public class HeartRateMessage {

    @JsonProperty("patientId")
    private String patientId;

    @JsonProperty("heartRate")
    private Integer heartRate;

    @JsonProperty("timestamp")
    private LocalDateTime timestamp;

    @JsonProperty("deviceId")
    private String deviceId;

    // Construtores
    public HeartRateMessage() {}

    public HeartRateMessage(String patientId, Integer heartRate, LocalDateTime timestamp, String deviceId) {
        this.patientId = patientId;
        this.heartRate = heartRate;
        this.timestamp = timestamp;
        this.deviceId = deviceId;
    }

    // Getters e Setters
    public String getPatientId() { return patientId; }
    public void setPatientId(String patientId) { this.patientId = patientId; }

    public Integer getHeartRate() { return heartRate; }
    public void setHeartRate(Integer heartRate) { this.heartRate = heartRate; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public String getDeviceId() { return deviceId; }
    public void setDeviceId(String deviceId) { this.deviceId = deviceId; }

    @Override
    public String toString() {
        return "HeartRateMessage{" +
                "patientId='" + patientId + '\'' +
                ", heartRate=" + heartRate +
                ", timestamp=" + timestamp +
                ", deviceId='" + deviceId + '\'' +
                '}';
    }
}