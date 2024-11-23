package com.goodcare.server.domain.login;

import com.goodcare.server.domain.caregiver.dao.Caregiver;
import com.goodcare.server.domain.caregiver.repository.CaregiverRepositoryBundle;
import com.goodcare.server.domain.patient.dao.patientinfo.Patient;
import com.goodcare.server.domain.patient.repository.PatientRepositoryBundle;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;


@Service
@AllArgsConstructor
public class LoginService {
    private final PatientRepositoryBundle  patientRepositoryBundle;
    private final CaregiverRepositoryBundle caregiverRepositoryBundle;

    public LoginResult login(LoginDTO loginDTO) {
        String userType = loginDTO.getUserType();
        String code = loginDTO.getCode();
        String name = loginDTO.getName();
        switch (userType) {
            case "환자" -> {
                Patient patient = patientRepositoryBundle.getPatientRepository()
                        .findPatientByCodeAndName(code, name).orElse(null);
                LoginResult loginResult = new LoginResult();
                if(patient != null) {
                    loginResult.object = patient;
                    loginResult.success = true;
                }else{
                    loginResult.object = null;
                    loginResult.success = false;
                }
                return loginResult;
            }
            case "간병인" -> {
                Caregiver caregiver = caregiverRepositoryBundle.getCaregiverRepository()
                        .findCaregiverByCodeAndName(code, name).orElse(null);
                LoginResult loginResult = new LoginResult();
                if(caregiver != null) {
                    loginResult.object = caregiver;
                    loginResult.success = true;
                }else{
                    loginResult.object = null;
                    loginResult.success = false;
                }
                return loginResult;
            }
            case "보호자" -> {
            }
        }
        return null;
    }
}
