package com.monitoring.api.service;

import com.monitoring.api.dto.BloodPressureRequest;
import com.monitoring.api.model.BloodPressureData;
import com.monitoring.api.repository.BloodPressureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MonitoringService {

    @Autowired
    private BloodPressureRepository bloodPressureRepository;

    public BloodPressureData saveBloodPressureData(BloodPressureRequest request) {
        BloodPressureData data = new BloodPressureData(
                request.getPatientId(),
                request.getSystolicPressure(),
                request.getDiastolicPressure()
        );
        return bloodPressureRepository.save(data);
    }

    public List<BloodPressureData> getBloodPressureByPatient(String patientId) {
        return bloodPressureRepository.findByPatientId(patientId);
    }

    // Busca dados mais recentes de um paciente (ordenado por ID)
    public List<BloodPressureData> getRecentBloodPressureByPatient(String patientId) {
        return bloodPressureRepository.findRecentByPatientId(patientId);
    }

    // Busca dados com pressão sistólica alta
    public List<BloodPressureData> getHighSystolicByPatient(String patientId, Integer minSystolic) {
        return bloodPressureRepository.findByPatientIdAndSystolicGreaterThan(patientId, minSystolic);
    }

    // Busca dados com pressão diastólica alta
    public List<BloodPressureData> getHighDiastolicByPatient(String patientId, Integer minDiastolic) {
        return bloodPressureRepository.findByPatientIdAndDiastolicGreaterThan(patientId, minDiastolic);
    }

    // Busca dados com pressão sistólica baixa
    public List<BloodPressureData> getLowSystolicByPatient(String patientId, Integer maxSystolic) {
        return bloodPressureRepository.findByPatientIdAndSystolicLessThan(patientId, maxSystolic);
    }

    // Busca dados com pressão diastólica baixa
    public List<BloodPressureData> getLowDiastolicByPatient(String patientId, Integer maxDiastolic) {
        return bloodPressureRepository.findByPatientIdAndDiastolicLessThan(patientId, maxDiastolic);
    }

    // Busca dados dentro de um intervalo de pressão sistólica
    public List<BloodPressureData> getSystolicInRangeByPatient(String patientId, Integer minSystolic, Integer maxSystolic) {
        return bloodPressureRepository.findByPatientIdAndSystolicBetween(patientId, minSystolic, maxSystolic);
    }

    // Busca dados dentro de um intervalo de pressão diastólica
    public List<BloodPressureData> getDiastolicInRangeByPatient(String patientId, Integer minDiastolic, Integer maxDiastolic) {
        return bloodPressureRepository.findByPatientIdAndDiastolicBetween(patientId, minDiastolic, maxDiastolic);
    }

    // Busca casos de hipertensão (pressão alta)
    public List<BloodPressureData> getHypertensionDataByPatient(String patientId) {
        return bloodPressureRepository.findHypertensionDataByPatientId(patientId);
    }

    // Busca casos de hipotensão (pressão baixa)
    public List<BloodPressureData> getHypotensionDataByPatient(String patientId) {
        return bloodPressureRepository.findHypotensionDataByPatientId(patientId);
    }

    // Conta quantos registros de pressão arterial um paciente tem
    public long countBloodPressureByPatient(String patientId) {
        return bloodPressureRepository.countByPatientId(patientId);
    }
}