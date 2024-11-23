package com.goodcare.server.domain.caregiver.repository;

import com.goodcare.server.domain.caregiver.dao.routine.CaregiverRoutine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CaregiverRoutineRepository extends JpaRepository<CaregiverRoutine, Long> {
    @Query("SELECT r FROM CaregiverRoutine r " +
            "WHERE r.patientCode = :patientCode ")
    List<CaregiverRoutine> findRoutinesByPatientCodeAndDate(
            @Param("patientCode") String patientCode
    );
}
