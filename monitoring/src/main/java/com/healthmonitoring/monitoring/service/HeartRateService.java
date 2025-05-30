package com.healthmonitoring.monitoring.service;

import com.healthmonitoring.monitoring.dto.HeartRateDataDTO;
import com.healthmonitoring.monitoring.entity.HeartRateEntity;
import com.healthmonitoring.monitoring.repository.HeartRateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class HeartRateService {

    private final HeartRateRepository heartRateRepository;

    @Transactional
    public HeartRateEntity saveHeartRateData(HeartRateDataDTO heartRateData) {
        log.info("💓 Salvando dados de batimento cardíaco para paciente: {}",
                heartRateData.getPatientId());

        HeartRateEntity entity = HeartRateEntity.builder()
                .patientId(heartRateData.getPatientId())
                .heartRate(heartRateData.getHeartRate())
                .timestamp(heartRateData.getTimestamp())
                .deviceId(heartRateData.getDeviceId())
                .status(heartRateData.getStatus())
                .build();

        HeartRateEntity saved = heartRateRepository.save(entity);
        log.info("✅ Dados de batimento salvos com ID: {}", saved.getId());

        return saved;
    }

    public List<HeartRateEntity> getHeartRateDataByPatient(String patientId) {
        log.info("📊 Buscando dados de batimento para paciente: {}", patientId);
        return heartRateRepository.findByPatientIdOrderByTimestampDesc(patientId);
    }

    public List<HeartRateEntity> getHeartRateDataByPatientAndPeriod(
            String patientId, LocalDateTime start, LocalDateTime end) {
        log.info("📅 Buscando dados de batimento para paciente {} entre {} e {}",
                patientId, start, end);
        return heartRateRepository.findByPatientIdAndTimestampBetweenOrderByTimestampDesc(
                patientId, start, end);
    }

    public Optional<HeartRateEntity> getLatestHeartRateData(String patientId) {
        log.info("🔍 Buscando último batimento para paciente: {}", patientId);
        return heartRateRepository.findFirstByPatientIdOrderByTimestampDesc(patientId);
    }

    public Double getAverageHeartRate(String patientId) {
        return heartRateRepository.findAverageHeartRateByPatientId(patientId);
    }

    public Integer getMinHeartRate(String patientId) {
        return heartRateRepository.findMinHeartRateByPatientId(patientId);
    }

    public Integer getMaxHeartRate(String patientId) {
        return heartRateRepository.findMaxHeartRateByPatientId(patientId);
    }
}