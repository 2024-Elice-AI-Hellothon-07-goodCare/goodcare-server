package com.goodcare.server.domain.patient.repository.dailychecklist;

import com.goodcare.server.domain.patient.dao.dailychecklist.PhysicalStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PhysicalStatusRepository extends JpaRepository<PhysicalStatus, Long> {
    public Optional<PhysicalStatus> findByCheckListCode(String checkListCode);
}
