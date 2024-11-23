package com.goodcare.server.domain.caregiver.dto;

import com.goodcare.server.domain.caregiver.dto.routine.CaregiverRoutineDTO;
import com.goodcare.server.domain.caregiver.dto.routine.CaregiverRoutineDayOfWeekDTO;
import com.goodcare.server.domain.caregiver.dto.routine.CaregiverRoutineStartTimeDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CaregiverRoutineDTOBundle {
    private CaregiverRoutineDTO caregiverRoutineDTO;
    private CaregiverRoutineDayOfWeekDTO caregiverRoutineDayOfWeekDTO;
    private CaregiverRoutineStartTimeDTO caregiverRoutineStartTimeDTO;
}
