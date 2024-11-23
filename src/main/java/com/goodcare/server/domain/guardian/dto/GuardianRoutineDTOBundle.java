package com.goodcare.server.domain.guardian.dto;

import com.goodcare.server.domain.guardian.dao.routine.GuardianRoutineStartTime;
import com.goodcare.server.domain.guardian.dto.routine.GuardianRoutineDTO;
import com.goodcare.server.domain.guardian.dto.routine.GuardianRoutineDayOfWeekDTO;
import com.goodcare.server.domain.guardian.dto.routine.GuardianRoutineStartTimeDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GuardianRoutineDTOBundle {
    private GuardianRoutineDTO guardianRoutineDTO;
    private GuardianRoutineDayOfWeekDTO guardianRoutineDayOfWeekDTO;
    private GuardianRoutineStartTimeDTO guardianRoutineStartTimeDTO;
}
