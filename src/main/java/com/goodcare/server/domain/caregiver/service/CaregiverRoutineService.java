package com.goodcare.server.domain.caregiver.service;

import com.goodcare.server.domain.caregiver.dao.Caregiver;
import com.goodcare.server.domain.caregiver.dto.routine.CaregiverRoutineStartTimeDTO;
import com.goodcare.server.domain.caregiver.repository.CaregiverRepositoryBundle;
import com.goodcare.server.domain.caregiver.dao.routine.CaregiverRoutine;
import com.goodcare.server.domain.caregiver.dao.routine.CaregiverRoutineDayOfWeek;
import com.goodcare.server.domain.caregiver.dao.routine.CaregiverRoutineStartTime;
import com.goodcare.server.domain.caregiver.dto.CaregiverRoutineDTOBundle;
import com.goodcare.server.domain.caregiver.dto.routine.CaregiverRoutineDTO;
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

        CaregiverRoutineStartTime caregiverRoutineStartTime = new CaregiverRoutineStartTime();
        List<LocalTime> startTimeList = caregiverRoutineDTOBundle.getCaregiverRoutineStartTimeDTO().getStartTime();

        for(LocalTime startTime : startTimeList){
            caregiverRoutineStartTime.setRoutineCode(routineCode);
            caregiverRoutineStartTime.setStartTime(startTime);
        }

        caregiverRepositoryBundle.getCaregiverRoutineStartTimeRepository().save(caregiverRoutineStartTime);

        CaregiverRoutineDayOfWeek caregiverRoutineDayOfWeek = new CaregiverRoutineDayOfWeek();
        List<String> dayOfWeekList = caregiverRoutineDTOBundle.getCaregiverRoutineDayOfWeekDTO().getDaysOfWeek();
        for(String day : dayOfWeekList){
            caregiverRoutineDayOfWeek.setRoutineCode(routineCode);
            caregiverRoutineDayOfWeek.setDaysOfWeek(day);
        }
        caregiverRepositoryBundle.getCaregiverRoutineDayOfWeekRepository().save(caregiverRoutineDayOfWeek);

        return true;
    }
}
