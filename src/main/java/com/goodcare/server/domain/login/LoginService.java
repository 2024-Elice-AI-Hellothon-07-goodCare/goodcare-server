package com.goodcare.server.domain.login;

import com.goodcare.server.domain.guardian.repository.GuardianRepositoryBundle;
import com.goodcare.server.domain.patient.dao.patientinfo.Patient;
import com.goodcare.server.domain.patient.repository.PatientRepositoryBundle;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LoginService {
    private final GuardianRepositoryBundle guardianRepositoryBundle;
    private final PatientRepositoryBundle  patientRepositoryBundle;

    public Boolean login(LoginDTO loginDTO) {
        String userType = loginDTO.getUserType();
        String code = loginDTO.getCode();
        String name = loginDTO.getName();
        if(userType.equals("환자")){
            Patient patient = patientRepositoryBundle.getPatientRepository()
                    .findPatientByCodeAndName(code, name).orElse(null);

            if(patient != null){
                return true;
            }else{
                return false;
            }
        }else if(userType.equals("간병인")){

        }else if(userType.equals("보호자")){

        }


        return false;
    }
}
