package com.monitoring.api.service;

import com.monitoring.api.dto.BloodPressureRequest;
import com.monitoring.api.model.BloodPressureData;
import com.monitoring.api.repository.BloodPressureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MonitoringService {

    @Autowired
    private BloodPressureRepository bloodPressureRepository;

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

    public List<BloodPressureData> getBloodPressureByPatient(String patientId) {
        return bloodPressureRepository.findByPatientIdOrderByTimestampDesc(patientId);
    }
}