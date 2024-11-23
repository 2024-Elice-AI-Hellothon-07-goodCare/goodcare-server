package com.goodcare.server.domain.caregiver.dao.routine;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
@Getter
@Setter
@Entity
@Table(name="Caregiver_routine_start_time")
public class CaregiverRoutineStartTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto Increment
    private Long id;

    @Column(name="routine_code", nullable = false)
    private String routineCode;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime; // 루틴 시작 시간
}
