package com.monitoring.api.repository;

import com.monitoring.api.model.MonitoringData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;

@Repository
public interface MonitoringRepository extends JpaRepository<MonitoringData, Long> {

    // Busca todos os dados de monitoramento de um paciente
    List<MonitoringData> findByPatientId(String patientId);

    // Busca os últimos registros de um paciente (ordenado por ID descendente)
    @Query("SELECT m FROM MonitoringData m WHERE m.patientId = :patientId ORDER BY m.id DESC")
    List<MonitoringData> findRecentByPatientId(@Param("patientId") String patientId);

    // Busca dados por nível de risco
    List<MonitoringData> findByRiskLevel(String riskLevel);

    // Busca dados por paciente e nível de risco
    List<MonitoringData> findByPatientIdAndRiskLevel(String patientId, String riskLevel);

    // Busca dados críticos de um paciente
    @Query("SELECT m FROM MonitoringData m WHERE m.patientId = :patientId AND m.riskLevel = 'HIGH'")
    List<MonitoringData> findCriticalDataByPatientId(@Param("patientId") String patientId);

    // Busca dados de alerta (WARNING) de um paciente
    @Query("SELECT m FROM MonitoringData m WHERE m.patientId = :patientId AND m.riskLevel = 'MEDIUM'")
    List<MonitoringData> findWarningDataByPatientId(@Param("patientId") String patientId);

    // Busca dados anormais (WARNING ou CRITICAL) de um paciente
    @Query("SELECT m FROM MonitoringData m WHERE m.patientId = :patientId AND m.riskLevel IN ('MEDIUM', 'HIGH')")
    List<MonitoringData> findAbnormalDataByPatientId(@Param("patientId") String patientId);

    // Busca dados com frequência cardíaca anormal
    @Query("SELECT m FROM MonitoringData m WHERE m.patientId = :patientId AND m.heartRate IS NOT NULL AND (m.heartRate > 100 OR m.heartRate < 60)")
    List<MonitoringData> findAbnormalHeartRateByPatientId(@Param("patientId") String patientId);

    // Busca dados com pressão alta
    @Query("SELECT m FROM MonitoringData m WHERE m.patientId = :patientId AND (m.systolicPressure >= 140 OR m.diastolicPressure >= 90)")
    List<MonitoringData> findHighBloodPressureByPatientId(@Param("patientId") String patientId);

    // Busca dados com pressão baixa
    @Query("SELECT m FROM MonitoringData m WHERE m.patientId = :patientId AND (m.systolicPressure < 90 OR m.diastolicPressure < 60)")
    List<MonitoringData> findLowBloodPressureByPatientId(@Param("patientId") String patientId);

    // Busca dados com frequência cardíaca acima de um valor
    @Query("SELECT m FROM MonitoringData m WHERE m.patientId = :patientId AND m.heartRate > :minRate")
    List<MonitoringData> findByPatientIdAndHeartRateGreaterThan(@Param("patientId") String patientId,
                                                                @Param("minRate") Integer minRate);

    // Busca dados com frequência cardíaca abaixo de um valor
    @Query("SELECT m FROM MonitoringData m WHERE m.patientId = :patientId AND m.heartRate < :maxRate")
    List<MonitoringData> findByPatientIdAndHeartRateLessThan(@Param("patientId") String patientId,
                                                             @Param("maxRate") Integer maxRate);

    // Busca dados com frequência cardíaca dentro de um intervalo
    @Query("SELECT m FROM MonitoringData m WHERE m.patientId = :patientId AND m.heartRate BETWEEN :minRate AND :maxRate")
    List<MonitoringData> findByPatientIdAndHeartRateBetween(@Param("patientId") String patientId,
                                                            @Param("minRate") Integer minRate,
                                                            @Param("maxRate") Integer maxRate);

    // Busca dados com pressão sistólica dentro de um intervalo
    @Query("SELECT m FROM MonitoringData m WHERE m.patientId = :patientId AND m.systolicPressure BETWEEN :minPressure AND :maxPressure")
    List<MonitoringData> findByPatientIdAndSystolicPressureBetween(@Param("patientId") String patientId,
                                                                   @Param("minPressure") Integer minPressure,
                                                                   @Param("maxPressure") Integer maxPressure);

    // Busca dados apenas com batimento cardíaco (sem pressão arterial)
    @Query("SELECT m FROM MonitoringData m WHERE m.patientId = :patientId AND m.heartRate IS NOT NULL AND (m.systolicPressure IS NULL OR m.diastolicPressure IS NULL)")
    List<MonitoringData> findHeartRateOnlyByPatientId(@Param("patientId") String patientId);

    // Busca dados apenas com pressão arterial (sem batimento cardíaco)
    @Query("SELECT m FROM MonitoringData m WHERE m.patientId = :patientId AND m.heartRate IS NULL AND m.systolicPressure IS NOT NULL AND m.diastolicPressure IS NOT NULL")
    List<MonitoringData> findBloodPressureOnlyByPatientId(@Param("patientId") String patientId);

    // Busca dados completos (com batimento e pressão)
    @Query("SELECT m FROM MonitoringData m WHERE m.patientId = :patientId AND m.heartRate IS NOT NULL AND m.systolicPressure IS NOT NULL AND m.diastolicPressure IS NOT NULL")
    List<MonitoringData> findCompleteDataByPatientId(@Param("patientId") String patientId);

    // Busca dados por período
    @Query("SELECT m FROM MonitoringData m WHERE m.patientId = :patientId")
    List<MonitoringData> findByPatientIdAndDateBetween(@Param("patientId") String patientId);

    // Busca dados críticos por período
    @Query("SELECT m FROM MonitoringData m WHERE m.patientId = :patientId AND m.riskLevel = 'HIGH'")
    List<MonitoringData> findCriticalDataByPatientIdAndDateBetween(@Param("patientId") String patientId);
}