package com.healthmonitoring.monitoring.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientSummaryDTO {

    private String patientId;
    private LocalDateTime lastUpdate;

    // Heart Rate Summary
    private Integer lastHeartRate;
    private Double avgHeartRate;
    private Integer minHeartRate;
    private Integer maxHeartRate;

    // Blood Pressure Summary
    private String lastBloodPressure; // "120/80"
    private Double avgSystolic;
    private Double avgDiastolic;
    private Integer minSystolic;
    private Integer maxSystolic;
    private Integer minDiastolic;
    private Integer maxDiastolic;

    // Alerts and Reports
    private String currentAlertLevel;
    private List<MonitoringReportDTO> recentReports;
    private Integer totalMeasurements;
}