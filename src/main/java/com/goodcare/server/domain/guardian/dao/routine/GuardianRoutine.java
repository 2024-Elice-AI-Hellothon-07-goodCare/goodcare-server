package com.goodcare.server.domain.guardian.dao.routine;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name="guardian_routine")
public class GuardianRoutine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto Increment
    private Long id;

    @Column(name="guardian_code", nullable = false)
    private String guardianCode;        // 간병인 본인의 코드

    @Column(name="patient_code", nullable = false)
    private String patientCode;         // 간병인 담당 환자의 코드

    @Column(name="routine_code", nullable = false)
    private String routineCode;

    @Column(name="routine_name", nullable = false)
    private String name; // 루틴 이름

    @Column(name = "is_important", nullable = false)
    private Boolean isImportant = false; // 중요 여부

    @Column(name = "frequency_type", nullable = false)
    private String frequencyType; // 반복 주기 유형 (매일, 주별 등)

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate; // 루틴 시작 날짜

    @Column(name = "end_date")
    private LocalDate endDate; // 루틴 종료 날짜 (NULL이면 계속 반복)

    @Column(name = "repeat_forever", nullable = false)
    private Boolean repeatForever = false; // 반복 무제한 여부

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt; // 생성 시간

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = null; // 수정 시간

    @Column(name = "completed")
    private Boolean completed = false; // 완료 여부
}
