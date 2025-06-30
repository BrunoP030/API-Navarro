package com.monitoring.model;

public class HeartRateData {
    private String patientId;
    private String patientName;
    private Integer heartRate;
    private String riskLevel;

    // Dados adicionais para passar no fluxo
    private Integer systolicPressure;
    private Integer diastolicPressure;

    // Construtor padrão
    public HeartRateData() {}

    // Construtor completo
    public HeartRateData(String patientId, String patientName, Integer heartRate,
                         Integer systolicPressure, Integer diastolicPressure) {
        this.patientId = patientId;
        this.patientName = patientName;
        this.heartRate = heartRate;
        this.systolicPressure = systolicPressure;
        this.diastolicPressure = diastolicPressure;
        this.riskLevel = calculateRiskLevel(heartRate);
    }

    // Construtor a partir de PatientData
    public HeartRateData(PatientData patientData) {
        this.patientId = patientData.getPatientId();
        this.patientName = patientData.getPatientName();
        this.heartRate = patientData.getHeartRate();
        this.systolicPressure = patientData.getSystolicPressure();
        this.diastolicPressure = patientData.getDiastolicPressure();
        this.riskLevel = calculateRiskLevel(heartRate);
    }

    private String calculateRiskLevel(Integer heartRate) {
        if (heartRate < 60) return "BAIXO";
        if (heartRate > 100) return "ALTO";
        return "NORMAL";
    }

    // Getters
    public String getPatientId() {
        return patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public Integer getHeartRate() {
        return heartRate;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public Integer getSystolicPressure() {
        return systolicPressure;
    }

    public Integer getDiastolicPressure() {
        return diastolicPressure;
    }

    // Setters
    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public void setHeartRate(Integer heartRate) {
        this.heartRate = heartRate;
        // Recalcula o nível de risco quando a frequência cardíaca muda
        this.riskLevel = calculateRiskLevel(heartRate);
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    public void setSystolicPressure(Integer systolicPressure) {
        this.systolicPressure = systolicPressure;
    }

    public void setDiastolicPressure(Integer diastolicPressure) {
        this.diastolicPressure = diastolicPressure;
    }

    @Override
    public String toString() {
        return "HeartRateData{" +
                "patientId='" + patientId + '\'' +
                ", patientName='" + patientName + '\'' +
                ", heartRate=" + heartRate +
                ", riskLevel='" + riskLevel + '\'' +
                ", systolicPressure=" + systolicPressure +
                ", diastolicPressure=" + diastolicPressure +
                '}';
    }
}