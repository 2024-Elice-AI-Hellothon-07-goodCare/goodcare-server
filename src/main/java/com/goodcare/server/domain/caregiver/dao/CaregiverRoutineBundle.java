package com.goodcare.server.domain.caregiver.dao;

import com.goodcare.server.domain.caregiver.dao.routine.CaregiverRoutine;
import com.goodcare.server.domain.caregiver.dao.routine.CaregiverRoutineDayOfWeek;
import com.goodcare.server.domain.caregiver.dao.routine.CaregiverRoutineStartTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CaregiverRoutineBundle {
    private CaregiverRoutine caregiverRoutine;
    private CaregiverRoutineDayOfWeek caregiverRoutineDayOfWeek;
    private CaregiverRoutineStartTime caregiverRoutineStartTime;
}
