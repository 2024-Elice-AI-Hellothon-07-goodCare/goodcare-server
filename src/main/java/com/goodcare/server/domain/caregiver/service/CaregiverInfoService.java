package com.goodcare.server.domain.caregiver.service;

import com.goodcare.server.domain.caregiver.controller.CaregiverInfoController;
import com.goodcare.server.domain.caregiver.dao.*;
import com.goodcare.server.domain.caregiver.dto.CaregiverDTOBundle;
import com.goodcare.server.domain.caregiver.dto.caregiver.CaregiverDTO;
import com.goodcare.server.domain.caregiver.repository.CaregiverRepositoryBundle;
import com.goodcare.server.domain.guardian.dao.Guardian;
import com.goodcare.server.domain.guardian.dao.GuardianConsent;
import com.goodcare.server.domain.guardian.dao.GuardianContactPreference;
import com.goodcare.server.domain.guardian.dto.GuardianDTOBundle;
import com.goodcare.server.domain.guardian.dto.guardian.GuardianDTO;
import com.goodcare.server.domain.guardian.repository.GuardianRepositoryBundle;
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

    public Boolean saveCaregiver(CaregiverDTOBundle caregiverDTOBundle) {
        CaregiverDTO caregiverDTO = caregiverDTOBundle.getCaregiverDTO();

        String caregiverCode = getUUID().substring(0, 6).toUpperCase();

        Patient patient = patientRepositoryBundle.getPatientRepository()
                .findByCode(caregiverDTO.getPatientCode()).orElse(null);

        if(patient == null){
            return false;
        }

        Caregiver caregiver = new Caregiver();

        caregiver.setCode(caregiverCode);
        caregiver.setPatientCode(caregiverDTO.getPatientCode());
        caregiver.setName(caregiverDTO.getName());
        caregiver.setDateOfBirth(caregiverDTO.getDateOfBirth());
        caregiver.setGender(caregiverDTO.getGender());
        caregiver.setContactNumber(caregiverDTO.getContactNumber());

        CaregiverExperience caregiverExperience = new CaregiverExperience();
        caregiverExperience.setCode(caregiverCode);
        caregiverExperience.setYearsOfExperience(caregiverDTOBundle.getCaregiverExperienceDTO()
                .getYearsOfExperience());
        caregiverExperience.setCompletedTraining(caregiverDTOBundle.getCaregiverExperienceDTO()
                .getCompletedTraining());
        caregiverExperience.setHasCertification(caregiverDTOBundle.getCaregiverExperienceDTO()
                .getHasCertification());

        CaregiverRole caregiverRole = new CaregiverRole();
        caregiverRole.setCode(caregiverCode);
        caregiverRole.setNeedsGuardianInfo(caregiverDTOBundle.getCaregiverRoleDTO()
                .getNeedsGuardianInfo());
        caregiverRole.setIsPrimeGaurdian(caregiverDTOBundle.getCaregiverRoleDTO()
                .getIsPrimeGaurdian());

        CaregiverSchedule caregiverSchedule = new CaregiverSchedule();
        caregiverSchedule.setCode(caregiverCode);
        caregiverSchedule.setDayOff(caregiverDTOBundle.getCaregiverScheduleDTO()
                .getDayOff());
        caregiverSchedule.setAvailableHours(caregiverDTOBundle.getCaregiverScheduleDTO()
                .getAvailableHours());

        CaregiverSpeciality caregiverSpeciality = new CaregiverSpeciality();
        caregiverSpeciality.setCode(caregiverCode);
        caregiverSpeciality.setSpecialPatientCareExperience(caregiverDTOBundle.getSpecialityDTO()
                .getSpecialPatientCareExperience());

        caregiverRepositoryBundle.getCaregiverRepository().save(caregiver);
        caregiverRepositoryBundle.getCaregiverExperienceRepository().save(caregiverExperience);
        caregiverRepositoryBundle.getCaregiverRoleRepository().save(caregiverRole);
        caregiverRepositoryBundle.getCaregiverScheduleRepository().save(caregiverSchedule);
        caregiverRepositoryBundle.getCaregiverSpecialityRepository().save(caregiverSpeciality);

        return true;
    }
}
