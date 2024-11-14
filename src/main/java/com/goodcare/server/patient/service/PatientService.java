package com.goodcare.server.patient.service;

import com.goodcare.server.patient.repository.PatientRepositoryBundle;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepositoryBundle patientRepositoryBundle;

}
