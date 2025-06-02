package com.monitoring.api.repository;

import com.monitoring.api.model.HeartRateData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface HeartRateRepository extends JpaRepository<HeartRateData, Long> {

    List<HeartRateData> findByPatientIdOrderByTimestampDesc(String patientId);

    @Query("SELECT h FROM HeartRateData h WHERE h.patientId = :patientId AND h.timestamp BETWEEN :startDate AND :endDate ORDER BY h.timestamp DESC")
    List<HeartRateData> findByPatientIdAndTimestampBetween(@Param("patientId") String patientId,
                                                           @Param("startDate") LocalDateTime startDate,
                                                           @Param("endDate") LocalDateTime endDate);
}