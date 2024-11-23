package com.goodcare.server.domain.caregiver.service;

import com.goodcare.server.domain.caregiver.dao.Caregiver;
import com.goodcare.server.domain.caregiver.repository.CaregiverRepositoryBundle;
import com.goodcare.server.domain.guardian.dao.Guardian;
import com.goodcare.server.domain.caregiver.dao.routine.CaregiverRoutine;
import com.goodcare.server.domain.caregiver.dao.routine.CaregiverRoutineDayOfWeek;
import com.goodcare.server.domain.caregiver.dao.routine.CaregiverRoutineStartTime;
import com.goodcare.server.domain.caregiver.dto.CaregiverRoutineDTOBundle;
import com.goodcare.server.domain.caregiver.dto.routine.CaregiverRoutineDTO;
import com.goodcare.server.domain.guardian.repository.GuardianRepositoryBundle;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.AlternativeJdkIdGenerator;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
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
        caregiverRoutineStartTime.setRoutineCode(routineCode);
        caregiverRoutineStartTime.setStartTime(caregiverRoutineDTOBundle
                .getCaregiverRoutineStartTimeDTO().getStartTime());

        caregiverRepositoryBundle.getCaregiverRoutineStartTimeRepository().save(caregiverRoutineStartTime);

        CaregiverRoutineDayOfWeek caregiverRoutineDayOfWeek = new CaregiverRoutineDayOfWeek();
        caregiverRoutineDayOfWeek.setRoutineCode(routineCode);
        caregiverRoutineDayOfWeek.setDaysOfWeek(URLEncoder.encode(caregiverRoutineDTOBundle
                .getCaregiverRoutineDayOfWeekDTO().getDaysOfWeek()
                , StandardCharsets.UTF_8));

        caregiverRepositoryBundle.getCaregiverRoutineDayOfWeekRepository().save(caregiverRoutineDayOfWeek);

        return true;
    }
}
