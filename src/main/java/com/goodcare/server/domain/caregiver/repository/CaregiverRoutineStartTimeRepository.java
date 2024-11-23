package com.goodcare.server.domain.caregiver.repository;

import com.goodcare.server.domain.caregiver.dao.routine.CaregiverRoutineStartTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface CaregiverRoutineStartTimeRepository extends JpaRepository<CaregiverRoutineStartTime, Long> {
    @Query("SELECT s.startTime FROM CaregiverRoutineStartTime s WHERE s.routineCode = :routineCode")
    List<LocalTime> findCaregiverRoutineStartTimeByRoutineCode(@Param("routineCode") String routineCode);
}

