package com.goodcare.server.domain.patient.dao;

import com.goodcare.server.domain.patient.dao.patientinfo.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PatientDAOBundle {
    private Patient patient;
    private EmergencyContact emergencyContact;
    private HealthInfo healthInfo;
    private MedicalServiceNeeds medicalServiceNeeds;
    private ResidentialInfo residentialInfo;
}
