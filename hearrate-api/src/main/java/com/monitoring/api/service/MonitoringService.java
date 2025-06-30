package com.monitoring.api.service;

import com.monitoring.api.dto.HeartRateRequest;
import com.monitoring.api.model.HeartRateData;
import com.monitoring.api.repository.HeartRateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class MonitoringService {

    private static final Logger logger = LoggerFactory.getLogger(MonitoringService.class);

    @Autowired
    private HeartRateRepository heartRateRepository;

    // Método existente para HeartRateRequest (mantido para compatibilidade)
    @Transactional
    public HeartRateData saveHeartRateData(HeartRateRequest request) {
        try {
            logger.info("Saving HeartRate data from request for patient: {}", request.getPatientId());

            HeartRateData data = new HeartRateData(
                    request.getPatientId(),
                    request.getHeartRate(),
                    request.getRiskLevel()
            );

            return heartRateRepository.save(data);

        } catch (Exception e) {
            logger.error("Error saving HeartRate data from request for patient {}: {}",
                    request.getPatientId(), e.getMessage(), e);
            throw new RuntimeException("Failed to save HeartRate data from request", e);
        }
    }

    // Novo método para HeartRateData (para o listener)
    @Transactional
    public HeartRateData saveHeartRateData(HeartRateData heartRateData) {
        try {
            logger.info("Saving HeartRate data directly for patient: {}", heartRateData.getPatientId());
            logger.debug("HeartRate data details: PatientId={}, HeartRate={}, RiskLevel={}",
                    heartRateData.getPatientId(),
                    heartRateData.getHeartRate(),
                    heartRateData.getRiskLevel());

            HeartRateData savedData = heartRateRepository.save(heartRateData);
            logger.info("HeartRate data saved successfully with ID: {} for patient: {}",
                    savedData.getId(), savedData.getPatientId());

            return savedData;
        } catch (Exception e) {
            logger.error("Error saving HeartRate data for patient {}: {}",
                    heartRateData.getPatientId(), e.getMessage(), e);
            throw new RuntimeException("Failed to save HeartRate data", e);
        }
    }

    public List<HeartRateData> getHeartRateByPatient(String patientId) {
        try {
            logger.debug("Fetching HeartRate data for patient: {}", patientId);
            List<HeartRateData> data = heartRateRepository.findByPatientId(patientId);
            logger.info("Found {} HeartRate records for patient: {}", data.size(), patientId);
            return data;
        } catch (Exception e) {
            logger.error("Error fetching HeartRate data for patient {}: {}", patientId, e.getMessage(), e);
            throw new RuntimeException("Failed to fetch HeartRate data", e);
        }
    }

    // Método para buscar dados recentes de um paciente (ordenado por ID)
    public List<HeartRateData> getRecentHeartRateByPatient(String patientId) {
        try {
            logger.debug("Fetching recent HeartRate data for patient: {}", patientId);
            List<HeartRateData> data = heartRateRepository.findRecentByPatientId(patientId);
            logger.info("Found {} recent HeartRate records for patient: {}", data.size(), patientId);
            return data;
        } catch (Exception e) {
            logger.error("Error fetching recent HeartRate data for patient {}: {}", patientId, e.getMessage(), e);
            throw new RuntimeException("Failed to fetch recent HeartRate data", e);
        }
    }

    // Método para buscar dados com frequência cardíaca acima de um valor
    public List<HeartRateData> getHighHeartRateByPatient(String patientId, Integer minRate) {
        try {
            logger.debug("Fetching high HeartRate data for patient: {} with minRate: {}", patientId, minRate);
            List<HeartRateData> data = heartRateRepository.findByPatientIdAndHeartRateGreaterThan(patientId, minRate);
            logger.info("Found {} high HeartRate records (>{}) for patient: {}", data.size(), minRate, patientId);
            return data;
        } catch (Exception e) {
            logger.error("Error fetching high HeartRate data for patient {}: {}", patientId, e.getMessage(), e);
            throw new RuntimeException("Failed to fetch high HeartRate data", e);
        }
    }

    // Método para buscar dados dentro de um intervalo de frequência cardíaca
    public List<HeartRateData> getHeartRateByPatientInRange(String patientId, Integer minRate, Integer maxRate) {
        try {
            logger.debug("Fetching HeartRate data for patient: {} in range: {}-{}", patientId, minRate, maxRate);
            List<HeartRateData> data = heartRateRepository.findByPatientIdAndHeartRateBetween(patientId, minRate, maxRate);
            logger.info("Found {} HeartRate records in range {}-{} for patient: {}",
                    data.size(), minRate, maxRate, patientId);
            return data;
        } catch (Exception e) {
            logger.error("Error fetching HeartRate data in range for patient {}: {}", patientId, e.getMessage(), e);
            throw new RuntimeException("Failed to fetch HeartRate data in range", e);
        }
    }

    // Método para contar quantos registros um paciente tem
    public long countHeartRateByPatient(String patientId) {
        try {
            logger.debug("Counting HeartRate records for patient: {}", patientId);
            long count = heartRateRepository.countByPatientId(patientId);
            logger.info("Patient {} has {} HeartRate records", patientId, count);
            return count;
        } catch (Exception e) {
            logger.error("Error counting HeartRate records for patient {}: {}", patientId, e.getMessage(), e);
            throw new RuntimeException("Failed to count HeartRate records", e);
        }
    }
}