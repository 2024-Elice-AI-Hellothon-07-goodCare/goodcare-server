package com.goodcare.server.domain.patient.repository;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Getter
public class PatientRepositoryBundle {
    private final PatientRepository patientRepository;
    private final HealthInfoRepository healthInfoRepository;
    private final EmergencyContactRepository emergencyContactRepository;
    private final MedicalServiceNeedsRepository medicalServiceNeedsRepository;
    private final ResidentialInfoRepository residentialInfoRepository;
}
