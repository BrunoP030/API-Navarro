package com.healthmonitoring.monitoring.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HeartRateDataDTO {

    @NotBlank(message = "Patient ID é obrigatório")
    private String patientId;

    @NotNull(message = "Heart Rate é obrigatório")
    @Positive(message = "Heart Rate deve ser positivo")
    private Integer heartRate;

    @NotNull(message = "Timestamp é obrigatório")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp;

    private String deviceId;
    private String status;
}