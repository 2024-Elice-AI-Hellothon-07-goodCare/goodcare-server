package com.goodcare.server.domain.guardian.service;

import com.goodcare.server.domain.guardian.dao.Guardian;
import com.goodcare.server.domain.guardian.dao.GuardianConsent;
import com.goodcare.server.domain.guardian.dao.GuardianContactPreference;
import com.goodcare.server.domain.guardian.dto.GuardianDTOBundle;
import com.goodcare.server.domain.guardian.dto.guardian.GuardianConsentDTO;
import com.goodcare.server.domain.guardian.dto.guardian.GuardianDTO;
import com.goodcare.server.domain.guardian.repository.GuardianRepositoryBundle;
import com.goodcare.server.domain.patient.dao.patientinfo.Patient;
import com.goodcare.server.domain.patient.repository.PatientRepositoryBundle;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.AlternativeJdkIdGenerator;
import org.webjars.NotFoundException;

import java.util.UUID;

@Service
@AllArgsConstructor
public class GuardianInfoService {
    private final GuardianRepositoryBundle guardianRepositoryBundle;
    private final PatientRepositoryBundle patientRepositoryBundle;

    private String getUUID(){
        UUID uuid = new AlternativeJdkIdGenerator().generateId();
        return uuid.toString();
    }

    public Boolean saveGuardian(GuardianDTOBundle guardianDTOBundle) {
        GuardianDTO guardianDTO = guardianDTOBundle.getGuardianDTO();

        String guardianCode = getUUID().substring(0, 6).toUpperCase();

        Patient patient = patientRepositoryBundle.getPatientRepository()
                .findByCode(guardianDTO.getPatientCode()).orElse(null);

        if(patient == null){
            return false;
        }

        Guardian guardian = new Guardian();

        guardian.setCode(guardianCode);
        guardian.setPatientCode(guardianDTO.getPatientCode());
        guardian.setName(guardianDTO.getName());
        guardian.setDateOfBirth(guardianDTO.getDateOfBirth());
        guardian.setGender(guardianDTO.getGender());
        guardian.setContactNumber(guardianDTO.getContactNumber());

        GuardianConsent guardianConsent = new GuardianConsent();
        guardianConsent.setCode(guardianCode);
        guardianConsent.setMedicalCareAccessConsent(guardianDTOBundle.getGuardianConsentDTO()
                .getMedicalCareAccessConsent());

        GuardianContactPreference guardianContactPreference = new GuardianContactPreference();
        guardianContactPreference.setCode(guardianCode);
        guardianContactPreference.setPreferredContactMethod(guardianDTOBundle.getGuardianContactPreferenceDTO()
                .getPreferredContactMethod());

        guardianRepositoryBundle.getGuardianRepository().save(guardian);
        guardianRepositoryBundle.getGuardianConsentRepository().save(guardianConsent);
        guardianRepositoryBundle.getGuardianContactPreferenceRepository().save(guardianContactPreference);

        return true;
    }
}
