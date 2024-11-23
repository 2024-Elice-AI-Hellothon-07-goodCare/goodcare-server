package com.goodcare.server.domain.caregiver.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.goodcare.server.domain.caregiver.dao.routine.CaregiverRoutine;
import com.goodcare.server.domain.caregiver.dto.routine.CaregiverRoutineDayOfWeekDTO;
import com.goodcare.server.domain.caregiver.dto.routine.CaregiverRoutineStartTimeDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchRoutineDTO {
    private CaregiverRoutine caregiverRoutine;
    private CaregiverRoutineStartTimeDTO caregiverRoutineStartTimeDTO;
    private CaregiverRoutineDayOfWeekDTO caregiverRoutineDayOfWeekDTO;
}
