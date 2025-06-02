package com.monitoring.api.service;

import com.monitoring.api.dto.HeartRateRequest;
import com.monitoring.api.model.HeartRateData;
import com.monitoring.api.repository.HeartRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MonitoringService {

    @Autowired
    private HeartRateRepository heartRateRepository;

    public HeartRateData saveHeartRateData(HeartRateRequest request) {
        HeartRateData data = new HeartRateData(
                request.getPatientId(),
                request.getHeartRate(),
                LocalDateTime.now(),
                request.getDeviceId()
        );
        return heartRateRepository.save(data);
    }

    public List<HeartRateData> getHeartRateByPatient(String patientId) {
        return heartRateRepository.findByPatientIdOrderByTimestampDesc(patientId);
    }
}