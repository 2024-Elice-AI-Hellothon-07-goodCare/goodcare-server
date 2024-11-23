package com.goodcare.server.domain.caregiver.repository;

import com.goodcare.server.domain.caregiver.dao.routine.CaregiverRoutineDayOfWeek;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CaregiverRoutineDayOfWeekRepository extends JpaRepository<CaregiverRoutineDayOfWeek, Long> {
    @Query("SELECT d.daysOfWeek FROM CaregiverRoutineDayOfWeek d WHERE d.routineCode = :routineCode")
    List<String> findCaregiverRoutineDayOfWeekByRoutineCode(@Param("routineCode") String routineCode);
}
