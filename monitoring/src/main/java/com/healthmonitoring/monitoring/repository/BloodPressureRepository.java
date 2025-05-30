package com.healthmonitoring.monitoring.repository;

import com.healthmonitoring.monitoring.entity.BloodPressureEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BloodPressureRepository extends JpaRepository<BloodPressureEntity, Long> {

    List<BloodPressureEntity> findByPatientIdOrderByTimestampDesc(String patientId);

    List<BloodPressureEntity> findByPatientIdAndTimestampBetweenOrderByTimestampDesc(
            String patientId, LocalDateTime start, LocalDateTime end);

    Optional<BloodPressureEntity> findFirstByPatientIdOrderByTimestampDesc(String patientId);

    @Query("SELECT AVG(b.systolic) FROM BloodPressureEntity b WHERE b.patientId = :patientId")
    Double findAverageSystolicByPatientId(@Param("patientId") String patientId);

    @Query("SELECT AVG(b.diastolic) FROM BloodPressureEntity b WHERE b.patientId = :patientId")
    Double findAverageDiastolicByPatientId(@Param("patientId") String patientId);

    @Query("SELECT MIN(b.systolic) FROM BloodPressureEntity b WHERE b.patientId = :patientId")
    Integer findMinSystolicByPatientId(@Param("patientId") String patientId);

    @Query("SELECT MAX(b.systolic) FROM BloodPressureEntity b WHERE b.patientId = :patientId")
    Integer findMaxSystolicByPatientId(@Param("patientId") String patientId);

    @Query("SELECT MIN(b.diastolic) FROM BloodPressureEntity b WHERE b.patientId = :patientId")
    Integer findMinDiastolicByPatientId(@Param("patientId") String patientId);

    @Query("SELECT MAX(b.diastolic) FROM BloodPressureEntity b WHERE b.patientId = :patientId")
    Integer findMaxDiastolicByPatientId(@Param("patientId") String patientId);

    Long countByPatientId(String patientId);

    List<BloodPressureEntity> findByPatientIdAndTimestampAfterOrderByTimestampDesc(
            String patientId, LocalDateTime timestamp);
}