package com.goodcare.server.domain.caregiver.service;

import com.goodcare.server.domain.caregiver.dao.*;
import com.goodcare.server.domain.caregiver.dto.caregiver.CaregiverDTO;
import com.goodcare.server.domain.caregiver.dto.caregiver.CaregiverRegisterDTO;
import com.goodcare.server.domain.caregiver.repository.CaregiverRepositoryBundle;
import com.goodcare.server.domain.patient.dao.patientinfo.Patient;
import com.goodcare.server.domain.patient.repository.PatientRepositoryBundle;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.AlternativeJdkIdGenerator;

import java.util.UUID;

@Service
@AllArgsConstructor
public class CaregiverInfoService {
    private final PatientRepositoryBundle patientRepositoryBundle;
    private final CaregiverRepositoryBundle caregiverRepositoryBundle;

    private String getUUID(){
        UUID uuid = new AlternativeJdkIdGenerator().generateId();
        return uuid.toString();
    }

    public CaregiverRegisterDTO saveCaregiver(CaregiverDTO caregiverDTO) {
        String caregiverCode = getUUID().substring(0, 6).toUpperCase();

        Patient patient = patientRepositoryBundle.getPatientRepository()
                .findByCode(caregiverDTO.getPatientCode()).orElse(null);

        if(patient == null){
            CaregiverRegisterDTO caregiverRegisterDTO = new CaregiverRegisterDTO();
            caregiverRegisterDTO.setCaregiver(null);
            caregiverRegisterDTO.setSuccess(false);
        }

        Caregiver caregiver = new Caregiver();

        caregiver.setCode(caregiverCode);
        caregiver.setPatientCode(caregiverDTO.getPatientCode());
        caregiver.setName(caregiverDTO.getName());
        caregiver.setDateOfBirth(caregiverDTO.getDateOfBirth());
        caregiver.setGender(caregiverDTO.getGender());
        caregiver.setContactNumber(caregiverDTO.getContactNumber());

        caregiverRepositoryBundle.getCaregiverRepository().save(caregiver);

        CaregiverRegisterDTO caregiverRegisterDTO = new CaregiverRegisterDTO();
        caregiverRegisterDTO.setCaregiver(caregiver);
        caregiverRegisterDTO.setSuccess(true);

        return caregiverRegisterDTO;
    }

    public String getPatientNameByCaregiverCode(String code){
        Caregiver caregiver = caregiverRepositoryBundle.getCaregiverRepository()
                .findCaregiverByCode(code).orElse(null);
        if(caregiver == null){
            return "간병인 정보가 없습니다.";
        }

        Patient patient = patientRepositoryBundle.getPatientRepository()
                .findByCode(caregiver.getPatientCode()).orElse(null);

        if(patient == null){
            return "환자 정보가 없습니다.";
        }

        return patient.getName();
    }
}
