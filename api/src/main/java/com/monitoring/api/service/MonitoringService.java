package com.monitoring.api.service;

import com.monitoring.api.dto.BloodPressureRequest;
import com.monitoring.api.dto.HeartRateRequest;
import com.monitoring.api.model.BloodPressureData;
import com.monitoring.api.model.HeartRateData;
import com.monitoring.api.repository.BloodPressureRepository;
import com.monitoring.api.repository.HeartRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MonitoringService {

    @Autowired
    private HeartRateRepository heartRateRepository;

    @Autowired
    private BloodPressureRepository bloodPressureRepository;

    public HeartRateData saveHeartRateData(HeartRateRequest request) {
        HeartRateData data = new HeartRateData(
                request.getPatientId(),
                request.getHeartRate(),
                LocalDateTime.now(),
                request.getDeviceId()
        );
        return heartRateRepository.save(data);
    }

    public BloodPressureData saveBloodPressureData(BloodPressureRequest request) {
        BloodPressureData data = new BloodPressureData(
                request.getPatientId(),
                request.getSystolic(),
                request.getDiastolic(),
                LocalDateTime.now(),
                request.getDeviceId()
        );
        return bloodPressureRepository.save(data);
    }

    public List<HeartRateData> getHeartRateByPatient(String patientId) {
        return heartRateRepository.findByPatientIdOrderByTimestampDesc(patientId);
    }

    public List<BloodPressureData> getBloodPressureByPatient(String patientId) {
        return bloodPressureRepository.findByPatientIdOrderByTimestampDesc(patientId);
    }
}