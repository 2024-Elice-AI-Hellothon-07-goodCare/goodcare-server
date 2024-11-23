package com.goodcare.server.domain.login;

import com.goodcare.server.domain.caregiver.dao.Caregiver;
import com.goodcare.server.domain.caregiver.repository.CaregiverRepositoryBundle;
import com.goodcare.server.domain.patient.dao.patientinfo.Patient;
import com.goodcare.server.domain.patient.repository.PatientRepositoryBundle;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class LoginService {
    private final PatientRepositoryBundle  patientRepositoryBundle;
    private final CaregiverRepositoryBundle caregiverRepositoryBundle;

    public Boolean login(LoginDTO loginDTO) {
        String userType = loginDTO.getUserType();
        String code = loginDTO.getCode();
        String name = loginDTO.getName();
        switch (userType) {
            case "환자" -> {
                Patient patient = patientRepositoryBundle.getPatientRepository()
                        .findPatientByCodeAndName(code, name).orElse(null);

                return patient != null;
            }
            case "간병인" -> {
                Caregiver Caregiver = caregiverRepositoryBundle.getCaregiverRepository()
                        .findCaregiverByCodeAndName(code, name).orElse(null);

                return Caregiver != null;
            }
            case "보호자" -> {
            }
        }


        return false;
    }
}
