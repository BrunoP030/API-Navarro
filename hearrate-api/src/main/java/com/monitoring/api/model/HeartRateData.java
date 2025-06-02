package com.monitoring.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

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

    @NotNull
    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @Column(name = "device_id")
    private String deviceId;

    // Construtores
    public HeartRateData() {}

    public HeartRateData(String patientId, Integer heartRate, LocalDateTime timestamp, String deviceId) {
        this.patientId = patientId;
        this.heartRate = heartRate;
        this.timestamp = timestamp;
        this.deviceId = deviceId;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPatientId() { return patientId; }
    public void setPatientId(String patientId) { this.patientId = patientId; }

    public Integer getHeartRate() { return heartRate; }
    public void setHeartRate(Integer heartRate) { this.heartRate = heartRate; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public String getDeviceId() { return deviceId; }
    public void setDeviceId(String deviceId) { this.deviceId = deviceId; }
}