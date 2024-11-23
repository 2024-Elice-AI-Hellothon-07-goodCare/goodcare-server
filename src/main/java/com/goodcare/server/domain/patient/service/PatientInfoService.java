package com.goodcare.server.domain.patient.service;

import com.goodcare.server.domain.patient.dao.patientinfo.*;
import com.goodcare.server.domain.patient.dto.patientinfodto.PatientDTO;
import com.goodcare.server.domain.patient.dto.patientinfodto.RegisterDTO;
import com.goodcare.server.domain.patient.repository.PatientRepositoryBundle;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.AlternativeJdkIdGenerator;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class PatientInfoService {
    private final PatientRepositoryBundle patientRepositoryBundle;

    private String getUUID(){
        UUID uuid = new AlternativeJdkIdGenerator().generateId();
        return uuid.toString();
    }

    public RegisterDTO registerPatient(PatientDTO patientDTO){
        // 먼저 환자 고유코드 만들기
        // 고유코드는 영문 대문자 + 숫자 조합의 6자리 코드
        String patientCode = getUUID().substring(0,6).toUpperCase();
        
        // 환자 기본정보 입력
        Patient patient = new Patient();
        patient.setCode(patientCode);
        patient.setName(patientDTO.getName());
        patient.setGender(patientDTO.getGender());
        patient.setDateOfBirth(patientDTO.getDateOfBirth());
        patient.setMainDisease(patientDTO.getMainDisease());
        patient.setUnderlyingDisease(patientDTO.getUnderlyingDisease());

        // 모든 DAO DB에 저장
        patientRepositoryBundle.getPatientRepository().save(patient);

        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setPatient(patient);
        registerDTO.setSuccess(true);

        return registerDTO;
    }

    public Patient searchPatientByCode(String code){
        Patient patient  = patientRepositoryBundle.getPatientRepository().findByCode(code)
                .orElseThrow(() -> new NotFoundException("환자 정보를 찾을 수 없습니다 - 환자 코드 : " + code));

        return patient;
    }

    public List<Patient> searchPatientsByName(String name){
        List<Patient> patientLists = new ArrayList<>();

        List<Patient> patientList = patientRepositoryBundle.getPatientRepository().findPatientsByName(name);
        log.debug("환자 " + patientList.size() + "건 발견!");
        for(int i = 0; i < patientList.size(); i++) {
            Patient patient = patientList.get(i);
            patientLists.add(patient);
        }

        return patientLists;
    }
}
