package com.goodcare.server.domain.caregiver.service;

import com.goodcare.server.domain.caregiver.dao.Caregiver;
import com.goodcare.server.domain.caregiver.dao.CaregiverRoutineBundle;
import com.goodcare.server.domain.caregiver.dto.SearchRoutineDTO;
import com.goodcare.server.domain.caregiver.dto.routine.CaregiverRoutineDayOfWeekDTO;
import com.goodcare.server.domain.caregiver.dto.routine.CaregiverRoutineStartTimeDTO;
import com.goodcare.server.domain.caregiver.repository.CaregiverRepositoryBundle;
import com.goodcare.server.domain.caregiver.dao.routine.CaregiverRoutine;
import com.goodcare.server.domain.caregiver.dao.routine.CaregiverRoutineDayOfWeek;
import com.goodcare.server.domain.caregiver.dao.routine.CaregiverRoutineStartTime;
import com.goodcare.server.domain.caregiver.dto.CaregiverRoutineDTOBundle;
import com.goodcare.server.domain.caregiver.dto.routine.CaregiverRoutineDTO;
import com.goodcare.server.domain.caregiver.repository.CaregiverRoutineRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.AlternativeJdkIdGenerator;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CaregiverRoutineService {
    private final CaregiverRepositoryBundle caregiverRepositoryBundle;
    private final CaregiverRoutineRepository caregiverRoutineRepository;

    private String getUUID(){
        UUID uuid = new AlternativeJdkIdGenerator().generateId();
        return uuid.toString();
    }


    public Boolean saveRoutine(CaregiverRoutineDTOBundle caregiverRoutineDTOBundle) {
        CaregiverRoutineDTO caregiverRoutineDTO = caregiverRoutineDTOBundle.getCaregiverRoutineDTO();
        String routineCode = getUUID().toUpperCase();

        Caregiver caregiver = caregiverRepositoryBundle.getCaregiverRepository()
                .findCaregiverByCode(caregiverRoutineDTO.getCaregiverCode())
                .orElse(null);

        if(caregiver == null){
            return false;
        }

        CaregiverRoutine caregiverRoutine = new CaregiverRoutine();
        caregiverRoutine.setPatientCode(caregiver.getPatientCode());
        caregiverRoutine.setGuardianCode(caregiver.getCode());
        caregiverRoutine.setRoutineCode(routineCode);
        caregiverRoutine.setName(URLEncoder.encode(caregiverRoutineDTO.getName(), StandardCharsets.UTF_8));
        caregiverRoutine.setIsImportant(caregiverRoutineDTO.getIsImportant());
        caregiverRoutine.setFrequencyType(caregiverRoutineDTO.getFrequencyType());
        caregiverRoutine.setStartDate(caregiverRoutineDTO.getStartDate());
        caregiverRoutine.setEndDate(caregiverRoutineDTO.getEndDate());
        caregiverRoutine.setRepeatForever(caregiverRoutineDTO.getRepeatForever());
        caregiverRoutine.setCreatedAt(LocalDateTime.now());  // 오늘 날짜

        caregiverRepositoryBundle.getCaregiverRoutineRepository().save(caregiverRoutine);

        List<LocalTime> startTimeList = caregiverRoutineDTOBundle.getCaregiverRoutineStartTimeDTO().getStartTime();

        for (LocalTime startTime : startTimeList) {
            CaregiverRoutineStartTime caregiverRoutineStartTime = new CaregiverRoutineStartTime(); // 반복문 내에서 객체 생성
            caregiverRoutineStartTime.setRoutineCode(routineCode);
            caregiverRoutineStartTime.setStartTime(startTime);
            caregiverRepositoryBundle.getCaregiverRoutineStartTimeRepository().save(caregiverRoutineStartTime); // 개별 저장
        }


        List<String> dayOfWeekList = caregiverRoutineDTOBundle.getCaregiverRoutineDayOfWeekDTO().getDaysOfWeek();
        for (String day : dayOfWeekList) {
            CaregiverRoutineDayOfWeek caregiverRoutineDayOfWeek = new CaregiverRoutineDayOfWeek(); // 반복문 내에서 객체 생성
            caregiverRoutineDayOfWeek.setRoutineCode(routineCode);
            caregiverRoutineDayOfWeek.setDaysOfWeek(day);
            caregiverRepositoryBundle.getCaregiverRoutineDayOfWeekRepository().save(caregiverRoutineDayOfWeek); // 매번 저장
        }

        return true;
    }

    public List<SearchRoutineDTO> searchRoutine(String code) {
        // 1. 특정 환자 코드와 날짜로 CaregiverRoutine 조회
        List<CaregiverRoutine> caregiverRoutines = caregiverRepositoryBundle.getCaregiverRoutineRepository()
                .findRoutinesByPatientCodeAndDate(code);

        List<SearchRoutineDTO> result = new ArrayList<>();

        for(CaregiverRoutine caregiverRoutine : caregiverRoutines){
            CaregiverRoutineDTOBundle caregiverRoutineDTOBundle = new CaregiverRoutineDTOBundle();
            String routineCode = caregiverRoutine.getRoutineCode();

            List<LocalTime> caregiverRoutineStartTimeList =
                    caregiverRepositoryBundle.getCaregiverRoutineStartTimeRepository()
                            .findCaregiverRoutineStartTimeByRoutineCode(routineCode);
            CaregiverRoutineStartTimeDTO routineStartTimeDTO = new CaregiverRoutineStartTimeDTO();
            routineStartTimeDTO.setStartTime(caregiverRoutineStartTimeList);


            List<String> caregiverRoutineDayOfWeekList =
                    caregiverRepositoryBundle.getCaregiverRoutineDayOfWeekRepository()
                            .findCaregiverRoutineDayOfWeekByRoutineCode(routineCode);
            CaregiverRoutineDayOfWeekDTO routineDayOfWeekDTO = new CaregiverRoutineDayOfWeekDTO();
            routineDayOfWeekDTO.setDaysOfWeek(caregiverRoutineDayOfWeekList);

            SearchRoutineDTO searchRoutineDTO = new SearchRoutineDTO();
            searchRoutineDTO.setCaregiverRoutine(caregiverRoutine);
            searchRoutineDTO.setCaregiverRoutineStartTimeDTO(routineStartTimeDTO);
            searchRoutineDTO.setCaregiverRoutineDayOfWeekDTO(routineDayOfWeekDTO);
            result.add(searchRoutineDTO);
        }

        return result;
    }
}
