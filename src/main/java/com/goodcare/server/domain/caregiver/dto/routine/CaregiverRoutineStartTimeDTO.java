package com.goodcare.server.domain.caregiver.dto.routine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CaregiverRoutineStartTimeDTO {
    private List<LocalTime> startTime; // 루틴 시작 시간
}
