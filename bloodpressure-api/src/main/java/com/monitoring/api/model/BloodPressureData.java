package com.monitoring.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

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
    @Column(name = "systolicPressure")
    private Integer systolicPressure;

    @NotNull
    @Min(30)
    @Max(200)
    @Column(name = "diastolicPressure")
    private Integer diastolicPressure;

    // Construtores
    public BloodPressureData() {}

    public BloodPressureData(String patientId, Integer systolicPressure, Integer diastolicPressure) {
        this.patientId = patientId;
        this.systolicPressure = systolicPressure;
        this.diastolicPressure = diastolicPressure;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPatientId() { return patientId; }
    public void setPatientId(String patientId) { this.patientId = patientId; }

    public Integer getSystolicPressure() { return systolicPressure; }
    public void setSystolicPressure(Integer systolicPressure) { this.systolicPressure = systolicPressure; }

    public Integer getDiastolicPressure() { return diastolicPressure; }
    public void setDiastolicPressure(Integer diastolicPressure) { this.diastolicPressure = diastolicPressure; }
}