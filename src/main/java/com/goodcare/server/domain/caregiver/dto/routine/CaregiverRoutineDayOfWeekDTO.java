package com.goodcare.server.domain.caregiver.dto.routine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CaregiverRoutineDayOfWeekDTO {
    private String daysOfWeek; // 요일 지정 (월, 화, 수 등)
}
