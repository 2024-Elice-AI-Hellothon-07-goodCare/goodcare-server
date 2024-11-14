package com.goodcare.server.patient.repository;

import com.goodcare.server.patient.dao.MedicalServiceNeeds;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicalServiceNeedsRepository extends JpaRepository<MedicalServiceNeeds, Long> {
}
