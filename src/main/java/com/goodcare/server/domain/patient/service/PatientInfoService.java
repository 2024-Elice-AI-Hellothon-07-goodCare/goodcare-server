package com.goodcare.server.domain.patient.service;

import com.goodcare.server.domain.patient.dao.*;
import com.goodcare.server.domain.patient.dao.patientinfo.*;
import com.goodcare.server.domain.patient.dto.PatientInfoDTO;
import com.goodcare.server.domain.patient.repository.PatientRepositoryBundle;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.parameters.P;
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

    public String registerPatient(PatientInfoDTO patientInfoDTO){
        // 먼저 환자 고유코드 만들기
        // 고유코드는 영문 대문자 + 숫자 조합의 6자리 코드
        String patientCode = getUUID().substring(0,6).toUpperCase();
        
        // 환자 기본정보 입력
        Patient patient = new Patient();
        patient.setCode(patientCode);
        patient.setName(patientInfoDTO.getPatientDTO().getName());
        patient.setGender(patientInfoDTO.getPatientDTO().getGender());
        patient.setContactNumber(patientInfoDTO.getPatientDTO().getContactNumber());
        patient.setDateOfBirth(patientInfoDTO.getPatientDTO().getDateOfBirth());

        // 건강 정보
        HealthInfo healthInfo = new HealthInfo();
        healthInfo.setPatientCode(patientCode);
        healthInfo.setAllergies(patientInfoDTO.getHealthInfoDTO().getAllergies());
        healthInfo.setMedications(patientInfoDTO.getHealthInfoDTO().getMedications());
        healthInfo.setDiseaseHistory(patientInfoDTO.getHealthInfoDTO().getDiseaseHistory());

        // 의료 정보
        MedicalServiceNeeds medicalServiceNeeds = new MedicalServiceNeeds();
        medicalServiceNeeds.setPatientCode(patientCode);
        medicalServiceNeeds.setTreatments(patientInfoDTO.getMedicalServiceNeedsDTO().getTreatments());
        medicalServiceNeeds.setSpecialNursing(patientInfoDTO.getMedicalServiceNeedsDTO().getSpecialNursing());
        medicalServiceNeeds.setRegularCheckups(patientInfoDTO.getMedicalServiceNeedsDTO().getRegularCheckups());

        // 비상 연락망
        EmergencyContact emergencyContact = new EmergencyContact();
        emergencyContact.setPatientCode(patientCode);
        emergencyContact.setEmergencyName(patientInfoDTO.getEmergencyContactDTO().getEmergencyName());
        emergencyContact.setRelationship(patientInfoDTO.getEmergencyContactDTO().getRelationship());
        emergencyContact.setEmergencyContact(patientInfoDTO.getEmergencyContactDTO().getEmergencyContact());

        // 거주지 정보
        ResidentialInfo residentialInfo = new ResidentialInfo();
        residentialInfo.setPatientCode(patientCode);
        residentialInfo.setResidence(patientInfoDTO.getResidentialInfoDTO().getResidence());

        // 모든 DAO DB에 저장
        patientRepositoryBundle.getPatientRepository().save(patient);
        patientRepositoryBundle.getHealthInfoRepository().save(healthInfo);
        patientRepositoryBundle.getMedicalServiceNeedsRepository().save(medicalServiceNeeds);
        patientRepositoryBundle.getEmergencyContactRepository().save(emergencyContact);
        patientRepositoryBundle.getResidentialInfoRepository().save(residentialInfo);

        return "success";
    }

    public PatientDAOBundle searchPatientByCode(String code){
        Patient patient  = patientRepositoryBundle.getPatientRepository().findByCode(code)
                .orElseThrow(() -> new NotFoundException("환자 정보를 찾을 수 없습니다 - 환자 코드 : " + code));

        return patientDAOBundleBuild(patient);
    }

    public List<PatientDAOBundle> searchPatientsByName(String name){
        List<PatientDAOBundle> patientDAOBundles = new ArrayList<>();

        List<Patient> patientList = patientRepositoryBundle.getPatientRepository().findPatientsByName(name);
        log.debug("환자 " + patientList.size() + "건 발견!");
        for(int i = 0; i < patientList.size(); i++) {
            Patient patient = patientList.get(i);
            patientDAOBundles.add(patientDAOBundleBuild(patient));
        }

        return patientDAOBundles;
    }
    
    // 번들 만드는 코드
    private PatientDAOBundle patientDAOBundleBuild(Patient patient){
        String patientCode = patient.getCode();

        EmergencyContact emergencyContact = patientRepositoryBundle.getEmergencyContactRepository()
                .findEmergencyContactByPatientCode(patientCode).orElse(null);
        HealthInfo healthInfo = patientRepositoryBundle.getHealthInfoRepository()
                .findHealthInfoByPatientCode(patientCode).orElse(null);
        MedicalServiceNeeds medicalServiceNeeds = patientRepositoryBundle.getMedicalServiceNeedsRepository()
                .findMedicalServiceNeedsByPatientCode(patientCode).orElse(null);
        ResidentialInfo residentialInfo = patientRepositoryBundle.getResidentialInfoRepository()
                .findResidentialInfoByPatientCode(patientCode).orElse(null);

        return new PatientDAOBundle(patient, emergencyContact, healthInfo, medicalServiceNeeds, residentialInfo);
    }
}
