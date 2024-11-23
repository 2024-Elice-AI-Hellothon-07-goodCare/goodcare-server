package com.goodcare.server.domain.guardian.repository;

import com.goodcare.server.domain.guardian.dao.routine.GuardianRoutineStartTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuardianRoutineStartTimeRepository extends JpaRepository<GuardianRoutineStartTime, Long> {
}
