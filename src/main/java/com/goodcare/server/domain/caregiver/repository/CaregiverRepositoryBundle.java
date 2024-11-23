package com.goodcare.server.domain.caregiver.repository;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Getter
public class CaregiverRepositoryBundle {
    private final CaregiverRepository caregiverRepository;
    private final CaregiverRoutineRepository caregiverRoutineRepository;
    private final CaregiverRoutineDayOfWeekRepository caregiverRoutineDayOfWeekRepository;
    private final CaregiverRoutineStartTimeRepository caregiverRoutineStartTimeRepository;
}
