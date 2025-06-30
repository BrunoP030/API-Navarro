package com.monitoring.api.service;

import com.monitoring.api.dto.MonitoringRequest;
import com.monitoring.api.model.MonitoringData;
import com.monitoring.api.repository.MonitoringRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MonitoringService {

    @Autowired
    private MonitoringRepository monitoringRepository;

    // ===== MÉTODOS PARA DADOS DE MONITORAMENTO =====

    public MonitoringData saveMonitoringData(MonitoringRequest request) {
        MonitoringData data = new MonitoringData(
                request.getPatientId(),
                request.getHeartRate(),
                request.getDiastolicPressure(),
                request.getSystolicPressure(),
                request.getRiskLevel()
        );
        return monitoringRepository.save(data);
    }

    // Corrigido: removido 'static' e usado instância do repository
    public List<MonitoringData> getMonitoringByPatient(String patientId) {
        return monitoringRepository.findByPatientId(patientId);
    }

    public List<MonitoringData> getRecentMonitoringByPatient(String patientId) {
        return monitoringRepository.findRecentByPatientId(patientId);
    }

    public List<MonitoringData> getCriticalDataByPatient(String patientId) {
        return monitoringRepository.findCriticalDataByPatientId(patientId);
    }

    public List<MonitoringData> getWarningDataByPatient(String patientId) {
        return monitoringRepository.findWarningDataByPatientId(patientId);
    }

    public List<MonitoringData> getAbnormalDataByPatient(String patientId) {
        return monitoringRepository.findAbnormalDataByPatientId(patientId);
    }

    // ===== MÉTODOS PARA FREQUÊNCIA CARDÍACA =====

    public List<MonitoringData> getAbnormalHeartRateByPatient(String patientId) {
        return monitoringRepository.findAbnormalHeartRateByPatientId(patientId);
    }

    public List<MonitoringData> getHighHeartRateByPatient(String patientId, Integer minRate) {
        return monitoringRepository.findByPatientIdAndHeartRateGreaterThan(patientId, minRate);
    }

    public List<MonitoringData> getLowHeartRateByPatient(String patientId, Integer maxRate) {
        return monitoringRepository.findByPatientIdAndHeartRateLessThan(patientId, maxRate);
    }

    public List<MonitoringData> getHeartRateByPatientInRange(String patientId, Integer minRate, Integer maxRate) {
        return monitoringRepository.findByPatientIdAndHeartRateBetween(patientId, minRate, maxRate);
    }

    public List<MonitoringData> getHeartRateOnlyByPatient(String patientId) {
        return monitoringRepository.findHeartRateOnlyByPatientId(patientId);
    }

    // ===== MÉTODOS PARA PRESSÃO ARTERIAL =====

    public List<MonitoringData> getHighBloodPressureByPatient(String patientId) {
        return monitoringRepository.findHighBloodPressureByPatientId(patientId);
    }

    public List<MonitoringData> getLowBloodPressureByPatient(String patientId) {
        return monitoringRepository.findLowBloodPressureByPatientId(patientId);
    }

    public List<MonitoringData> getSystolicPressureInRange(String patientId, Integer minPressure, Integer maxPressure) {
        return monitoringRepository.findByPatientIdAndSystolicPressureBetween(patientId, minPressure, maxPressure);
    }

    public List<MonitoringData> getBloodPressureOnlyByPatient(String patientId) {
        return monitoringRepository.findBloodPressureOnlyByPatientId(patientId);
    }

    // ===== MÉTODOS PARA DADOS COMPLETOS =====

    public List<MonitoringData> getCompleteDataByPatient(String patientId) {
        return monitoringRepository.findCompleteDataByPatientId(patientId);
    }

    // ===== MÉTODOS PARA FILTROS POR NÍVEL DE RISCO =====

    public List<MonitoringData> getDataByRiskLevel(String riskLevel) {
        return monitoringRepository.findByRiskLevel(riskLevel);
    }

    public List<MonitoringData> getDataByPatientAndRiskLevel(String patientId, String riskLevel) {
        return monitoringRepository.findByPatientIdAndRiskLevel(patientId, riskLevel);
    }

    // ===== MÉTODOS PARA RELATÓRIOS E ESTATÍSTICAS =====

    public List<MonitoringData> getAllDataByPatient(String patientId) {
        return monitoringRepository.findByPatientIdAndDateBetween(patientId);
    }

    public List<MonitoringData> getCriticalDataByPatientInPeriod(String patientId) {
        return monitoringRepository.findCriticalDataByPatientIdAndDateBetween(patientId);
    }
}