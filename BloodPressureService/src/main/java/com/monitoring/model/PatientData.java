package com.monitoring.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public class PatientData {

    private String patientId;
    private String patientName;
    private Integer age;
    private String gender;

    // Heart Rate data
    private Integer heartRate;
    private String heartRateStatus;

    // Blood Pressure data
    private Integer systolicPressure;
    private Integer diastolicPressure;
    private String bloodPressureStatus;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;

    // Constructors
    public PatientData() {
        this.timestamp = LocalDateTime.now();
    }

    public PatientData(String patientId, String patientName) {
        this();
        this.patientId = patientId;
        this.patientName = patientName;
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(Integer heartRate) {
        this.heartRate = heartRate;
    }

    public String getHeartRateStatus() {
        return heartRateStatus;
    }

    public void setHeartRateStatus(String heartRateStatus) {
        this.heartRateStatus = heartRateStatus;
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

    public String getBloodPressureStatus() {
        return bloodPressureStatus;
    }

    public void setBloodPressureStatus(String bloodPressureStatus) {
        this.bloodPressureStatus = bloodPressureStatus;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "PatientData{" +
                "patientId='" + patientId + '\'' +
                ", patientName='" + patientName + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", heartRate=" + heartRate +
                ", heartRateStatus='" + heartRateStatus + '\'' +
                ", systolicPressure=" + systolicPressure +
                ", diastolicPressure=" + diastolicPressure +
                ", bloodPressureStatus='" + bloodPressureStatus + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}