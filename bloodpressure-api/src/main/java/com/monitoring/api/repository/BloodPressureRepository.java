package com.monitoring.api.repository;

import com.monitoring.api.model.BloodPressureData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BloodPressureRepository extends JpaRepository<BloodPressureData, Long> {

    // Busca todos os dados de pressão arterial de um paciente
    List<BloodPressureData> findByPatientId(String patientId);

    // Busca os dados mais recentes de um paciente (ordenado por ID descendente)
    @Query("SELECT b FROM BloodPressureData b WHERE b.patientId = :patientId ORDER BY b.id DESC")
    List<BloodPressureData> findRecentByPatientId(@Param("patientId") String patientId);

    // Busca por pressão sistólica acima de um valor
    @Query("SELECT b FROM BloodPressureData b WHERE b.patientId = :patientId AND b.systolicPressure > :minSystolicPressure ORDER BY b.id DESC")
    List<BloodPressureData> findByPatientIdAndSystolicGreaterThan(@Param("patientId") String patientId,
                                                                  @Param("minSystolicPressure") Integer minSystolicPressure);

    // Busca por pressão diastólica acima de um valor
    @Query("SELECT b FROM BloodPressureData b WHERE b.patientId = :patientId AND b.diastolicPressure > :minDiastolicPressure ORDER BY b.id DESC")
    List<BloodPressureData> findByPatientIdAndDiastolicGreaterThan(@Param("patientId") String patientId,
                                                                   @Param("minDiastolicPressure") Integer minDiastolicPressure);

    // Busca por pressão sistólica abaixo de um valor
    @Query("SELECT b FROM BloodPressureData b WHERE b.patientId = :patientId AND b.systolicPressure < :maxSystolicPressure ORDER BY b.id DESC")
    List<BloodPressureData> findByPatientIdAndSystolicLessThan(@Param("patientId") String patientId,
                                                               @Param("maxSystolicPressure") Integer maxSystolicPressure);

    // Busca por pressão diastólica abaixo de um valor
    @Query("SELECT b FROM BloodPressureData b WHERE b.patientId = :patientId AND b.diastolicPressure < :maxDiastolicPressure ORDER BY b.id DESC")
    List<BloodPressureData> findByPatientIdAndDiastolicLessThan(@Param("patientId") String patientId,
                                                                @Param("maxDiastolicPressure") Integer maxDiastolicPressure);

    // Busca por intervalo de pressão sistólica
    @Query("SELECT b FROM BloodPressureData b WHERE b.patientId = :patientId AND b.systolicPressure BETWEEN :minSystolicPressure AND :maxSystolicPressure ORDER BY b.id DESC")
    List<BloodPressureData> findByPatientIdAndSystolicBetween(@Param("patientId") String patientId,
                                                              @Param("minSystolicPressure") Integer minSystolicPressure,
                                                              @Param("maxSystolicPressure") Integer maxSystolicPressure);

    // Busca por intervalo de pressão diastólica
    @Query("SELECT b FROM BloodPressureData b WHERE b.patientId = :patientId AND b.diastolicPressure BETWEEN :minDiastolicPressure AND :maxDiastolicPressure ORDER BY b.id DESC")
    List<BloodPressureData> findByPatientIdAndDiastolicBetween(@Param("patientId") String patientId,
                                                               @Param("minDiastolicPressure") Integer minDiastolicPressure,
                                                               @Param("maxDiastolicPressure") Integer maxDiastolicPressure);

    // Busca dados com pressão alta (hipertensão) - sistólica >= 140 OU diastólica >= 90
    @Query("SELECT b FROM BloodPressureData b WHERE b.patientId = :patientId AND (b.systolicPressure >= 140 OR b.diastolicPressure >= 90) ORDER BY b.id DESC")
    List<BloodPressureData> findHypertensionDataByPatientId(@Param("patientId") String patientId);

    // Busca dados com pressão baixa (hipotensão) - sistólica < 90 OU diastólica < 60
    @Query("SELECT b FROM BloodPressureData b WHERE b.patientId = :patientId AND (b.systolicPressure < 90 OR b.diastolicPressure < 60) ORDER BY b.id DESC")
    List<BloodPressureData> findHypotensionDataByPatientId(@Param("patientId") String patientId);

    // Conta quantos registros um paciente tem
    long countByPatientId(String patientId);
}