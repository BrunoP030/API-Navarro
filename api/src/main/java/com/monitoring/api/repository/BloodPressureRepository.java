package com.monitoring.api.repository;

import com.monitoring.api.model.BloodPressureData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BloodPressureRepository extends JpaRepository<BloodPressureData, Long> {

    List<BloodPressureData> findByPatientIdOrderByTimestampDesc(String patientId);

    @Query("SELECT b FROM BloodPressureData b WHERE b.patientId = :patientId AND b.timestamp BETWEEN :startDate AND :endDate ORDER BY b.timestamp DESC")
    List<BloodPressureData> findByPatientIdAndTimestampBetween(@Param("patientId") String patientId,
                                                               @Param("startDate") LocalDateTime startDate,
                                                               @Param("endDate") LocalDateTime endDate);
}
