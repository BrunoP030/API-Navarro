package com.monitoring.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "blood_pressure_data")
public class BloodPressureData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "patient_id")
    private String patientId;

    @NotNull
    @Min(50)
    @Max(300)
    @Column(name = "systolic")
    private Integer systolic;

    @NotNull
    @Min(30)
    @Max(200)
    @Column(name = "diastolic")
    private Integer diastolic;

    @NotNull
    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @Column(name = "device_id")
    private String deviceId;

    // Construtores
    public BloodPressureData() {}

    public BloodPressureData(String patientId, Integer systolic, Integer diastolic,
                             LocalDateTime timestamp, String deviceId) {
        this.patientId = patientId;
        this.systolic = systolic;
        this.diastolic = diastolic;
        this.timestamp = timestamp;
        this.deviceId = deviceId;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPatientId() { return patientId; }
    public void setPatientId(String patientId) { this.patientId = patientId; }

    public Integer getSystolic() { return systolic; }
    public void setSystolic(Integer systolic) { this.systolic = systolic; }

    public Integer getDiastolic() { return diastolic; }
    public void setDiastolic(Integer diastolic) { this.diastolic = diastolic; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public String getDeviceId() { return deviceId; }
    public void setDeviceId(String deviceId) { this.deviceId = deviceId; }
}