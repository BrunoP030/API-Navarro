package com.monitoring.model;

public class BloodPressureData {
    private String patientId;
    private String patientName;
    private Integer systolicPressure;
    private Integer diastolicPressure;
    private String classification;

    // Construtor padrão
    public BloodPressureData() {}

    // Construtor completo
    public BloodPressureData(String patientId, String patientName, Integer systolicPressure, Integer diastolicPressure) {
        this.patientId = patientId;
        this.patientName = patientName;
        this.systolicPressure = systolicPressure;
        this.diastolicPressure = diastolicPressure;
        this.classification = calculateClassification(systolicPressure, diastolicPressure);
    }

    // Construtor a partir de PatientData
    public BloodPressureData(PatientData patientData) {
        this.patientId = patientData.getPatientId();
        this.patientName = patientData.getPatientName();
        this.systolicPressure = patientData.getSystolicPressure();
        this.diastolicPressure = patientData.getDiastolicPressure();
        this.classification = calculateClassification(systolicPressure, diastolicPressure);
    }

    private String calculateClassification(Integer systolicPressure, Integer diastolicPressure) {
        if (systolicPressure < 120 && diastolicPressure < 80) return "NORMAL";
        if (systolicPressure < 130 && diastolicPressure < 80) return "ELEVADA";
        if (systolicPressure < 140 || diastolicPressure < 90) return "HIPERTENSAO_ESTAGIO_1";
        if (systolicPressure < 180 || diastolicPressure < 120) return "HIPERTENSAO_ESTAGIO_2";
        return "CRISE_HIPERTENSIVA";
    }

    // Getters
    public String getPatientId() {
        return patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public Integer getSystolicPressure() {
        return systolicPressure;
    }

    public Integer getDiastolicPressure() {
        return diastolicPressure;
    }

    public String getClassification() {
        return classification;
    }

    // Setters
    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public void setSystolicPressure(Integer systolicPressure) {
        this.systolicPressure = systolicPressure;
        // Recalcula a classificação quando a pressão sistólica muda
        this.classification = calculateClassification(this.systolicPressure, this.diastolicPressure);
    }

    public void setDiastolicPressure(Integer diastolicPressure) {
        this.diastolicPressure = diastolicPressure;
        // Recalcula a classificação quando a pressão diastólica muda
        this.classification = calculateClassification(this.systolicPressure, this.diastolicPressure);
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