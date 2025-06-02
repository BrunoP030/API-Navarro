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

    // Busca os dados mais recentes de um paciente (ordenado por ID)
    @Query("SELECT b FROM BloodPressureData b WHERE b.patientId = :patientId ORDER BY b.id DESC")
    List<BloodPressureData> findRecentByPatientId(@Param("patientId") String patientId);

    // Busca por pressão sistólica acima de um valor
    @Query("SELECT b FROM BloodPressureData b WHERE b.patientId = :patientId AND b.systolic > :minSystolic ORDER BY b.id DESC")
    List<BloodPressureData> findByPatientIdAndSystolicGreaterThan(@Param("patientId") String patientId,
                                                                  @Param("minSystolic") Integer minSystolic);

    // Busca por pressão diastólica acima de um valor
    @Query("SELECT b FROM BloodPressureData b WHERE b.patientId = :patientId AND b.diastolic > :minDiastolic ORDER BY b.id DESC")
    List<BloodPressureData> findByPatientIdAndDiastolicGreaterThan(@Param("patientId") String patientId,
                                                                   @Param("minDiastolic") Integer minDiastolic);

    // Busca por intervalo de pressão sistólica
    @Query("SELECT b FROM BloodPressureData b WHERE b.patientId = :patientId AND b.systolic BETWEEN :minSystolic AND :maxSystolic ORDER BY b.id DESC")
    List<BloodPressureData> findByPatientIdAndSystolicBetween(@Param("patientId") String patientId,
                                                              @Param("minSystolic") Integer minSystolic,
                                                              @Param("maxSystolic") Integer maxSystolic);

    // Busca por intervalo de pressão diastólica
    @Query("SELECT b FROM BloodPressureData b WHERE b.patientId = :patientId AND b.diastolic BETWEEN :minDiastolic AND :maxDiastolic ORDER BY b.id DESC")
    List<BloodPressureData> findByPatientIdAndDiastolicBetween(@Param("patientId") String patientId,
                                                               @Param("minDiastolic") Integer minDiastolic,
                                                               @Param("maxDiastolic") Integer maxDiastolic);

    // Busca dados com pressão alta (hipertensão) - sistólica >= 140 OU diastólica >= 90
    @Query("SELECT b FROM BloodPressureData b WHERE b.patientId = :patientId AND (b.systolic >= 140 OR b.diastolic >= 90) ORDER BY b.id DESC")
    List<BloodPressureData> findHypertensionDataByPatientId(@Param("patientId") String patientId);

    // Conta quantos registros um paciente tem
    long countByPatientId(String patientId);
}
