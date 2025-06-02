package com.monitoring.api.service;

import com.monitoring.api.dto.BloodPressureRequest;
import com.monitoring.api.dto.HeartRateRequest;
import com.monitoring.api.model.BloodPressureData;
import com.monitoring.api.model.HeartRateData;
import com.monitoring.api.repository.BloodPressureRepository;
import com.monitoring.api.repository.HeartRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MonitoringService {

    @Autowired
    private HeartRateRepository heartRateRepository;

    @Autowired
    private BloodPressureRepository bloodPressureRepository;

    // ===== MÉTODOS PARA FREQUÊNCIA CARDÍACA =====

    public HeartRateData saveHeartRateData(HeartRateRequest request) {
        HeartRateData data = new HeartRateData(
                request.getPatientId(),
                request.getHeartRate(),
                request.getDeviceId()
        );
        return heartRateRepository.save(data);
    }

    public List<HeartRateData> getHeartRateByPatient(String patientId) {
        return heartRateRepository.findByPatientId(patientId);
    }

    public List<HeartRateData> getRecentHeartRateByPatient(String patientId) {
        return heartRateRepository.findRecentByPatientId(patientId);
    }

    public List<HeartRateData> getHighHeartRateByPatient(String patientId, Integer minRate) {
        return heartRateRepository.findByPatientIdAndHeartRateGreaterThan(patientId, minRate);
    }

    public List<HeartRateData> getHeartRateByPatientInRange(String patientId, Integer minRate, Integer maxRate) {
        return heartRateRepository.findByPatientIdAndHeartRateBetween(patientId, minRate, maxRate);
    }

    public long countHeartRateByPatient(String patientId) {
        return heartRateRepository.countByPatientId(patientId);
    }

    // ===== MÉTODOS PARA PRESSÃO ARTERIAL =====

    public BloodPressureData saveBloodPressureData(BloodPressureRequest request) {
        BloodPressureData data = new BloodPressureData(
                request.getPatientId(),
                request.getSystolic(),
                request.getDiastolic(),
                request.getDeviceId()
        );
        return bloodPressureRepository.save(data);
    }

    public List<BloodPressureData> getBloodPressureByPatient(String patientId) {
        return bloodPressureRepository.findByPatientId(patientId);
    }

    public List<BloodPressureData> getRecentBloodPressureByPatient(String patientId) {
        return bloodPressureRepository.findRecentByPatientId(patientId);
    }

    public List<BloodPressureData> getHighSystolicByPatient(String patientId, Integer minSystolic) {
        return bloodPressureRepository.findByPatientIdAndSystolicGreaterThan(patientId, minSystolic);
    }

    public List<BloodPressureData> getHighDiastolicByPatient(String patientId, Integer minDiastolic) {
        return bloodPressureRepository.findByPatientIdAndDiastolicGreaterThan(patientId, minDiastolic);
    }

    public List<BloodPressureData> getSystolicInRangeByPatient(String patientId, Integer minSystolic, Integer maxSystolic) {
        return bloodPressureRepository.findByPatientIdAndSystolicBetween(patientId, minSystolic, maxSystolic);
    }

    public List<BloodPressureData> getDiastolicInRangeByPatient(String patientId, Integer minDiastolic, Integer maxDiastolic) {
        return bloodPressureRepository.findByPatientIdAndDiastolicBetween(patientId, minDiastolic, maxDiastolic);
    }

    public List<BloodPressureData> getHypertensionDataByPatient(String patientId) {
        return bloodPressureRepository.findHypertensionDataByPatientId(patientId);
    }

    public long countBloodPressureByPatient(String patientId) {
        return bloodPressureRepository.countByPatientId(patientId);
    }
}