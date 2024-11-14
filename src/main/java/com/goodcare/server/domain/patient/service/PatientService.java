package com.goodcare.server.domain.patient.service;

import com.goodcare.server.domain.patient.repository.PatientRepositoryBundle;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PatientService {
    private final PatientRepositoryBundle patientRepositoryBundle;

}
