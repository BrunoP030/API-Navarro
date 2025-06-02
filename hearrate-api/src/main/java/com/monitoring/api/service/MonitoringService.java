package com.monitoring.api.service;

import com.monitoring.api.dto.HeartRateRequest;
import com.monitoring.api.model.HeartRateData;
import com.monitoring.api.repository.HeartRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MonitoringService {

    @Autowired
    private HeartRateRepository heartRateRepository;

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

    // Método para buscar dados recentes de um paciente (ordenado por ID)
    public List<HeartRateData> getRecentHeartRateByPatient(String patientId) {
        return heartRateRepository.findRecentByPatientId(patientId);
    }

    // Método para buscar dados com frequência cardíaca acima de um valor
    public List<HeartRateData> getHighHeartRateByPatient(String patientId, Integer minRate) {
        return heartRateRepository.findByPatientIdAndHeartRateGreaterThan(patientId, minRate);
    }

    // Método para buscar dados dentro de um intervalo de frequência cardíaca
    public List<HeartRateData> getHeartRateByPatientInRange(String patientId, Integer minRate, Integer maxRate) {
        return heartRateRepository.findByPatientIdAndHeartRateBetween(patientId, minRate, maxRate);
    }

    // Método para contar quantos registros um paciente tem
    public long countHeartRateByPatient(String patientId) {
        return heartRateRepository.countByPatientId(patientId);
    }
}