package com.goodcare.server.domain.caregiver.repository;

import com.goodcare.server.domain.caregiver.dao.routine.CaregiverRoutineStartTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CaregiverRoutineStartTimeRepository extends JpaRepository<CaregiverRoutineStartTime, Long> {
}
