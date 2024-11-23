package com.goodcare.server.domain.caregiver.repository;

import com.goodcare.server.domain.caregiver.dao.routine.CaregiverRoutine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CaregiverRoutineRepository extends JpaRepository<CaregiverRoutine, Long> {
}
