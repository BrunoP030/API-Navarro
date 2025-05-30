package com.healthmonitoring.monitoring.service;

import com.healthmonitoring.monitoring.dto.BloodPressureDataDTO;
import com.healthmonitoring.monitoring.entity.BloodPressureEntity;
import com.healthmonitoring.monitoring.repository.BloodPressureRepository;
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
public class BloodPressureService {

    private final BloodPressureRepository bloodPressureRepository;

    @Transactional
    public BloodPressureEntity saveBloodPressureData(BloodPressureDataDTO bloodPressureData) {
        log.info("🩸 Salvando dados de pressão arterial para paciente: {}",
                bloodPressureData.getPatientId());

        BloodPressureEntity entity = BloodPressureEntity.builder()
                .patientId(bloodPressureData.getPatientId())
                .systolic(bloodPressureData.getSystolic())
                .diastolic(bloodPressureData.getDiastolic())
                .timestamp(bloodPressureData.getTimestamp())
                .deviceId(bloodPressureData.getDeviceId())
                .status(bloodPressureData.getStatus())
                .build();

        BloodPressureEntity saved = bloodPressureRepository.save(entity);
        log.info("✅ Dados de pressão salvos com ID: {}", saved.getId());

        return saved;
    }

    public List<BloodPressureEntity> getBloodPressureDataByPatient(String patientId) {
        log.info("📊 Buscando dados de pressão para paciente: {}", patientId);
        return bloodPressureRepository.findByPatientIdOrderByTimestampDesc(patientId);
    }

    public List<BloodPressureEntity> getBloodPressureDataByPatientAndPeriod(
            String patientId, LocalDateTime start, LocalDateTime end) {
        log.info("📅 Buscando dados de pressão para paciente {} entre {} e {}",
                patientId, start, end);
        return bloodPressureRepository.findByPatientIdAndTimestampBetweenOrderByTimestampDesc(
                patientId, start, end);
    }

    public Optional<BloodPressureEntity> getLatestBloodPressureData(String patientId) {
        log.info("🔍 Busc

