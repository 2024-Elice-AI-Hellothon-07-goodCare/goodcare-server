package com.goodcare.server.domain.patient.repository.patientinfo;

import com.goodcare.server.domain.patient.dao.patientinfo.MedicalServiceNeeds;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedicalServiceNeedsRepository extends JpaRepository<MedicalServiceNeeds, Long> {
    public Optional<MedicalServiceNeeds> findMedicalServiceNeedsByPatientCode(String code);
}
