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
public class BloodPressureDataDTO {

    @NotBlank(message = "Patient ID é obrigatório")
    private String patientId;

    @NotNull(message = "Pressão sistólica é obrigatória")
    @Positive(message = "Pressão sistólica deve ser positiva")
    private Integer systolic;

    @NotNull(message = "Pressão diastólica é obrigatória")
    @Positive(message = "Pressão diastólica deve ser positiva")
    private Integer diastolic;

    @NotNull(message = "Timestamp é obrigatório")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp;

    private String deviceId;
    private String status;
}