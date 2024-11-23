package com.goodcare.server.domain.guardian.dto.routine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GuardianRoutineDayOfWeekDTO {
    private String daysOfWeek; // 요일 지정 (월, 화, 수 등)
}
