package com.goodcare.server.domain.guardian.service;

import com.goodcare.server.domain.guardian.dao.Guardian;
import com.goodcare.server.domain.guardian.dao.routine.GuardianRoutine;
import com.goodcare.server.domain.guardian.dao.routine.GuardianRoutineDayOfWeek;
import com.goodcare.server.domain.guardian.dao.routine.GuardianRoutineStartTime;
import com.goodcare.server.domain.guardian.dto.GuardianRoutineDTOBundle;
import com.goodcare.server.domain.guardian.dto.routine.GuardianRoutineDTO;
import com.goodcare.server.domain.guardian.dto.routine.GuardianRoutineDayOfWeekDTO;
import com.goodcare.server.domain.guardian.repository.GuardianRepositoryBundle;
import com.goodcare.server.domain.guardian.repository.GuardianRoutineRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.AlternativeJdkIdGenerator;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class GuardianRoutineService {
    private final GuardianRepositoryBundle guardianRepositoryBundle;

    private String getUUID(){
        UUID uuid = new AlternativeJdkIdGenerator().generateId();
        return uuid.toString();
    }


    public Boolean saveRoutine(GuardianRoutineDTOBundle guardianRoutineDTOBundle) {
        GuardianRoutineDTO guardianRoutineDTO = guardianRoutineDTOBundle.getGuardianRoutineDTO();
        String routineCode = getUUID().toUpperCase();

        Guardian guardian = guardianRepositoryBundle.getGuardianRepository()
                .findGuardianByCode(guardianRoutineDTO.getGuardianCode())
                .orElse(null);

        if(guardian == null){
            return false;
        }

        GuardianRoutine guardianRoutine = new GuardianRoutine();
        guardianRoutine.setPatientCode(guardian.getPatientCode());
        guardianRoutine.setGuardianCode(guardian.getCode());
        guardianRoutine.setRoutineCode(routineCode);
        guardianRoutine.setName(URLEncoder.encode(guardianRoutineDTO.getName(), StandardCharsets.UTF_8));
        guardianRoutine.setIsImportant(guardianRoutineDTO.getIsImportant());
        guardianRoutine.setFrequencyType(guardianRoutineDTO.getFrequencyType());
        guardianRoutine.setStartDate(guardianRoutineDTO.getStartDate());
        guardianRoutine.setEndDate(guardianRoutineDTO.getEndDate());
        guardianRoutine.setRepeatForever(guardianRoutineDTO.getRepeatForever());
        guardianRoutine.setCreatedAt(LocalDateTime.now());  // 오늘 날짜

        guardianRepositoryBundle.getGuardianRoutineRepository().save(guardianRoutine);

        GuardianRoutineStartTime guardianRoutineStartTime = new GuardianRoutineStartTime();
        guardianRoutineStartTime.setRoutineCode(routineCode);
        guardianRoutineStartTime.setStartTime(guardianRoutineDTOBundle
                .getGuardianRoutineStartTimeDTO().getStartTime());

        guardianRepositoryBundle.getGuardianRoutineStartTimeRepository().save(guardianRoutineStartTime);

        GuardianRoutineDayOfWeek guardianRoutineDayOfWeek = new GuardianRoutineDayOfWeek();
        guardianRoutineDayOfWeek.setRoutineCode(routineCode);
        guardianRoutineDayOfWeek.setDaysOfWeek(URLEncoder.encode(guardianRoutineDTOBundle
                .getGuardianRoutineDayOfWeekDTO().getDaysOfWeek()
                , StandardCharsets.UTF_8));

        guardianRepositoryBundle.getGuardianRoutineDayOfWeekRepository().save(guardianRoutineDayOfWeek);

        return true;
    }
}
