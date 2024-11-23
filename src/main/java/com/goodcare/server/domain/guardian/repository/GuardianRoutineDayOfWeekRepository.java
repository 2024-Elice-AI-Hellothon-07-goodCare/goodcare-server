package com.goodcare.server.domain.guardian.repository;

import com.goodcare.server.domain.guardian.dao.routine.GuardianRoutineDayOfWeek;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuardianRoutineDayOfWeekRepository extends JpaRepository<GuardianRoutineDayOfWeek, Long> {
}
