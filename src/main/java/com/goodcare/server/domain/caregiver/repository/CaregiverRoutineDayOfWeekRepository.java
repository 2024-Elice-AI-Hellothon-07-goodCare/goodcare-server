package com.goodcare.server.domain.caregiver.repository;

import com.goodcare.server.domain.caregiver.dao.routine.CaregiverRoutineDayOfWeek;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CaregiverRoutineDayOfWeekRepository extends JpaRepository<CaregiverRoutineDayOfWeek, Long> {
}
