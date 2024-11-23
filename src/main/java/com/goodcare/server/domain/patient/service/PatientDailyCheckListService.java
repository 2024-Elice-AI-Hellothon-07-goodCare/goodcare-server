package com.goodcare.server.domain.patient.service;

import com.goodcare.server.domain.patient.dao.dailychecklist.*;
import com.goodcare.server.domain.patient.dao.patientinfo.Patient;
import com.goodcare.server.domain.patient.dto.PatientDailyCheckListDTOBundle;
import com.goodcare.server.domain.patient.repository.PatientRepositoryBundle;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.AlternativeJdkIdGenerator;
import org.webjars.NotFoundException;

import java.time.LocalDate;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PatientDailyCheckListService {

    private PatientRepositoryBundle patientRepositoryBundle;

    private String getUUID(){
        UUID uuid = new AlternativeJdkIdGenerator().generateId();
        return uuid.toString();
    }

    // 오늘 날짜의 체크리스트가 있는지 확인하는 로직
    public Boolean isChecklistCreatedToday(LocalDate date, String code){
        DailyCheckList checkTodayDailyCheckList = patientRepositoryBundle.getDailyCheckListRepository()
                .findDailyCheckListByTodayAndPatientCode(date, code).orElse(null);
        return checkTodayDailyCheckList != null;
    }

    // 체크리스트 생성 로직
    public Boolean saveDailyCheckList(
        PatientDailyCheckListDTOBundle patientDailyCheckListDTOBundle,
        String code
    ){
        Patient patient = patientRepositoryBundle.getPatientRepository().findByCode(code)
                .orElseThrow(() -> new NotFoundException("환자 정보를 찾을 수 없습니다. 환자 코드 : " + code));
        String patientCode = patient.getCode();

        // 오늘 날짜 이미 체크리스트 입력한 경우 더 이상 입력하지 못함.
        if(isChecklistCreatedToday(LocalDate.now(), patientCode)){
            return false;
        }

        DailyCheckList dailyCheckList = new DailyCheckList();
        String dailyCheckListCode = getUUID();
        dailyCheckList.setCode(dailyCheckListCode);
        dailyCheckList.setPatientCode(patientCode);
        dailyCheckList.setCreatedAt(LocalDate.now());

        VitalSigns vitalSigns = new VitalSigns();
        vitalSigns.setCheckListCode(dailyCheckListCode);
        vitalSigns.setTemperature(patientDailyCheckListDTOBundle.getVitalSignsDTO().getTemperature());
        vitalSigns.setBloodPressureSys(patientDailyCheckListDTOBundle.getVitalSignsDTO().getBloodPressureSys());
        vitalSigns.setBloodPressureDia(patientDailyCheckListDTOBundle.getVitalSignsDTO().getBloodPressureDia());
        vitalSigns.setPulse(patientDailyCheckListDTOBundle.getVitalSignsDTO().getPulse());
        vitalSigns.setOxygen(patientDailyCheckListDTOBundle.getVitalSignsDTO().getOxygen());
        vitalSigns.setRespirationRate(patientDailyCheckListDTOBundle.getVitalSignsDTO().getRespirationRate());

        Consciousness consciousness = new Consciousness();
        consciousness.setCheckListCode(dailyCheckListCode);
        consciousness.setConsciousnessLevel(patientDailyCheckListDTOBundle.getConsciousnessDTO().getConsciousnessLevel());
        consciousness.setMoodBehaviour(patientDailyCheckListDTOBundle.getConsciousnessDTO().getMoodBehaviour());

        PhysicalStatus physicalStatus = new PhysicalStatus();
        physicalStatus.setCheckListCode(dailyCheckListCode);
        physicalStatus.setSkinCondition(patientDailyCheckListDTOBundle.getPhysicalStatusDTO().getSkinCondition());
        physicalStatus.setPainLevel(patientDailyCheckListDTOBundle.getPhysicalStatusDTO().getPainLevel());
        physicalStatus.setPainLocation(patientDailyCheckListDTOBundle.getPhysicalStatusDTO().getPainLocation());
        physicalStatus.setMobility(patientDailyCheckListDTOBundle.getPhysicalStatusDTO().getMobility());

        Medications medications = new Medications();
        medications.setCheckListCode(dailyCheckListCode);
        medications.setMedicationTaken(patientDailyCheckListDTOBundle.getMedicationsDTO().getMedicationTaken());
        medications.setSideEffects(patientDailyCheckListDTOBundle.getMedicationsDTO().getSideEffects());

        SpecialNotes specialNotes = new SpecialNotes();
        specialNotes.setCheckListCode(dailyCheckListCode);
        specialNotes.setSpecialNotes(patientDailyCheckListDTOBundle.getSpecialNotesDTO().getSpecialNotes());
        specialNotes.setCaregiverNotes(patientDailyCheckListDTOBundle.getSpecialNotesDTO().getCaregiverNotes());

        patientRepositoryBundle.getDailyCheckListRepository().save(dailyCheckList);
        patientRepositoryBundle.getVitalSignsRepository().save(vitalSigns);
        patientRepositoryBundle.getConsciousnessRepository().save(consciousness);
        patientRepositoryBundle.getPhysicalStatusRepository().save(physicalStatus);
        patientRepositoryBundle.getMedicationsRepository().save(medications);
        patientRepositoryBundle.getSpecialNotesRepository().save(specialNotes);

        return true;
    }
}
