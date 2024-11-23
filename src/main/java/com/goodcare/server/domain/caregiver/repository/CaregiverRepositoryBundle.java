package com.goodcare.server.domain.caregiver.repository;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Getter
public class CaregiverRepositoryBundle {
    private final CaregiverRepository caregiverRepository;
    private final CaregiverExperienceRepository caregiverExperienceRepository;
    private final CaregiverRoleRepository caregiverRoleRepository;
    private final CaregiverScheduleRepository caregiverScheduleRepository;
    private final CaregiverSpecialityRepository caregiverSpecialityRepository;
    private final CaregiverRoutineRepository caregiverRoutineRepository;
    private final CaregiverRoutineDayOfWeekRepository caregiverRoutineDayOfWeekRepository;
    private final CaregiverRoutineStartTimeRepository caregiverRoutineStartTimeRepository;
}
