package com.healthmonitoring.monitoring.repository;

import com.healthmonitoring.monitoring.entity.MonitoringReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MonitoringReportRepository extends JpaRepository<MonitoringReportEntity, Long> {

    List<MonitoringReportEntity> findByPatientIdOrderByGeneratedAtDesc(String patientId);

    List<MonitoringReportEntity> findByPatientIdAndReportTypeOrderByGeneratedAtDesc(
            String patientId, String reportType);

    List<MonitoringReportEntity> findByPatientIdAndAlertLevelOrderByGeneratedAtDesc(
            String patientId, String alertLevel);

    List<MonitoringReportEntity> findByPatientIdAndGeneratedAtBetweenOrderByGeneratedAtDesc(
            String patientId, LocalDateTime start, LocalDateTime end);

    Optional<MonitoringReportEntity> findFirstByPatientIdOrderByGeneratedAtDesc(String patientId);

    @Query("SELECT r FROM MonitoringReportEntity r WHERE r.patientId = :patientId " +
            "ORDER BY r.generatedAt DESC LIMIT :limit")
    List<MonitoringReportEntity> findRecentReportsByPatientId(
            @Param("patientId") String patientId, @Param("limit") int limit);

    List<MonitoringReportEntity> findByAlertLevelInOrderByGeneratedAtDesc(List<String> alertLevels);

    Long countByPatientIdAndAlertLevel(String patientId, String alertLevel);
}