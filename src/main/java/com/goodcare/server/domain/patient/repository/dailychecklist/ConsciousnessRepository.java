package com.goodcare.server.domain.patient.repository.dailychecklist;

import com.goodcare.server.domain.patient.dao.dailychecklist.Consciousness;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConsciousnessRepository extends JpaRepository<Consciousness, Long> {
    public Optional<Consciousness> findByCheckListCode(String code);
}
