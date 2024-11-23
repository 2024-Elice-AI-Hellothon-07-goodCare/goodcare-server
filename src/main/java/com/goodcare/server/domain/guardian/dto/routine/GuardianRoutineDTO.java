package com.goodcare.server.domain.guardian.dto.routine;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GuardianRoutineDTO {
    private String guardianCode; // 간병인 코드
    
    private String name; // 루틴 이름

    private Boolean isImportant; // 중요 여부

    private String frequencyType; // 반복 주기 유형 (매일, 주별 등)

    private LocalDate startDate; // 루틴 시작 날짜

    private LocalDate endDate; // 루틴 종료 날짜 (NULL이면 계속 반복)

    private Boolean repeatForever; // 반복 무제한 여부
}
