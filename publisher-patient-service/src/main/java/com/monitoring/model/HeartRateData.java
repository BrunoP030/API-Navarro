package com.monitoring.model;

public class HeartRateData {
    private String patientId;
    private String patientName;
    private Integer heartRate;
    private String riskLevel;


    public HeartRateData() {}

    public HeartRateData(PatientData patientData) {
        this.patientId = patientData.getPatientId();
        this.patientName = patientData.getPatientName();
        this.heartRate = patientData.getHeartRate();
        this.riskLevel = calculateRiskLevel(heartRate);
    }

    private String calculateRiskLevel(Integer heartRate) {
        if (heartRate < 60) return "BAIXO";
        if (heartRate > 100) return "ALTO";
        return "NORMAL";
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
                "pacienteId='" + patientId + '\'' +
                ", pacienteNome='" + patientName + '\'' +
                ", frequenciaCardiaca=" + heartRate +
                ", lvlRisco='" + riskLevel + '\'' +
                '}';
    }
}
