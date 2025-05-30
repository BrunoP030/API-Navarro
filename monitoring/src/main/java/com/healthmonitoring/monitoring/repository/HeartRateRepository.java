package com.healthmonitoring.monitoring.repository;

import com.healthmonitoring.monitoring.entity.HeartRateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface HeartRateRepository extends JpaRepository<HeartRateEntity, Long> {

    List<HeartRateEntity> findByPatientIdOrderByTimestampDesc(String patientId);

    List<HeartRateEntity> findByPatientIdAndTimestampBetweenOrderByTimestampDesc(
            String patientId, LocalDateTime start, LocalDateTime end);

    Optional<HeartRateEntity> findFirstByPatientIdOrderByTimestampDesc(String patientId);

    @Query("SELECT AVG(h.heartRate) FROM HeartRateEntity h WHERE h.patientId = :patientId")
    Double findAverageHeartRateByPatientId(@Param("patientId") String patientId);

    @Query("SELECT MIN(h.heartRate) FROM HeartRateEntity h WHERE h.patientId = :patientId")
    Integer findMinHeartRateByPatientId(@Param("patientId") String patientId);

    @Query("SELECT MAX(h.heartRate) FROM HeartRateEntity h WHERE h.patientId = :patientId")
    Integer findMaxHeartRateByPatientId(@Param("patientId") String patientId);

    Long countByPatientId(String patientId);

    List<HeartRateEntity> findByPatientIdAndTimestampAfterOrderByTimestampDesc(
            String patientId, LocalDateTime timestamp);
}