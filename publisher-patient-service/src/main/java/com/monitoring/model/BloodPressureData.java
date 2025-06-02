package com.monitoring.model;

public class BloodPressureData {
    private String patientId;
    private String patientName;
    private Integer systolicPressure;
    private Integer diastolicPressure;
    private String classification;

    public BloodPressureData() {}

    public BloodPressureData(PatientData patientData) {
        this.patientId = patientData.getPatientId();
        this.patientName = patientData.getPatientName();
        this.systolicPressure = patientData.getSystolicPressure();
        this.diastolicPressure = patientData.getDiastolicPressure();
        this.classification = calculateClassification(systolicPressure, diastolicPressure);
    }

    private String calculateClassification(Integer systolic, Integer diastolic) {
        if (systolic < 120 && diastolic < 80) return "NORMAL";
        if (systolic < 130 && diastolic < 80) return "ELEVADA";
        if (systolic < 140 || diastolic < 90) return "HIPERTENSAO_ESTAGIO_1";
        if (systolic < 180 || diastolic < 120) return "HIPERTENSAO_ESTAGIO_2";
        return "CRISE_HIPERTENSIVA";
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
                "pacienteId='" + patientId + '\'' +
                ", pacienteNome='" + patientName + '\'' +
                ", pressaoSistolica=" + systolicPressure +
                ", pressaoDiastolica=" + diastolicPressure +
                ", classificação='" + classification + '\'' +
                '}';
    }
}