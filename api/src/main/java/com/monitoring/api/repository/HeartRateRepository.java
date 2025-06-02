package com.monitoring.api.repository;

import com.monitoring.api.model.HeartRateData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface HeartRateRepository extends JpaRepository<HeartRateData, Long> {

    // Busca todos os dados de frequência cardíaca de um paciente
    List<HeartRateData> findByPatientId(String patientId);

    // Busca os últimos registros de um paciente (ordenado por ID descendente)
    @Query("SELECT h FROM HeartRateData h WHERE h.patientId = :patientId ORDER BY h.id DESC")
    List<HeartRateData> findRecentByPatientId(@Param("patientId") String patientId);

    // Busca dados de frequência cardíaca acima de um valor específico
    @Query("SELECT h FROM HeartRateData h WHERE h.patientId = :patientId AND h.heartRate > :minRate ORDER BY h.id DESC")
    List<HeartRateData> findByPatientIdAndHeartRateGreaterThan(@Param("patientId") String patientId,
                                                               @Param("minRate") Integer minRate);

    // Busca dados de frequência cardíaca abaixo de um valor específico
    @Query("SELECT h FROM HeartRateData h WHERE h.patientId = :patientId AND h.heartRate < :maxRate ORDER BY h.id DESC")
    List<HeartRateData> findByPatientIdAndHeartRateLessThan(@Param("patientId") String patientId,
                                                            @Param("maxRate") Integer maxRate);

    // Busca dados de frequência cardíaca dentro de um intervalo
    @Query("SELECT h FROM HeartRateData h WHERE h.patientId = :patientId AND h.heartRate BETWEEN :minRate AND :maxRate ORDER BY h.id DESC")
    List<HeartRateData> findByPatientIdAndHeartRateBetween(@Param("patientId") String patientId,
                                                           @Param("minRate") Integer minRate,
                                                           @Param("maxRate") Integer maxRate);

    // Busca dados de frequência cardíaca anormal (muito alta ou muito baixa)
    @Query("SELECT h FROM HeartRateData h WHERE h.patientId = :patientId AND (h.heartRate > 100 OR h.heartRate < 60) ORDER BY h.id DESC")
    List<HeartRateData> findAbnormalHeartRateByPatientId(@Param("patientId") String patientId);

    // Conta quantos registros um paciente tem
    long countByPatientId(String patientId);

    // Busca por device específico
    List<HeartRateData> findByPatientIdAndDeviceId(String patientId, String deviceId);
}