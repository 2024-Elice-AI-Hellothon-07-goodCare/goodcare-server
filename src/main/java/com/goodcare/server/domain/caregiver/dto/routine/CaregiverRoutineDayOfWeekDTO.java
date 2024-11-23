package com.goodcare.server.domain.caregiver.dto.routine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CaregiverRoutineDayOfWeekDTO {
    private List<String> daysOfWeek; // 요일 지정 (월, 화, 수 등)
}
