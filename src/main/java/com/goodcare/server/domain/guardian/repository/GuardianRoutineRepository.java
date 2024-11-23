package com.goodcare.server.domain.guardian.repository;

import com.goodcare.server.domain.guardian.dao.routine.GuardianRoutine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuardianRoutineRepository extends JpaRepository<GuardianRoutine, Long> {
}
