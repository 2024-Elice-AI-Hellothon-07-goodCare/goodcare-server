package com.goodcare.server.domain.caregiver.dto;

import com.goodcare.server.domain.caregiver.dto.caregiver.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CaregiverDTOBundle {
    private CaregiverDTO caregiverDTO;
    private CaregiverExperienceDTO caregiverExperienceDTO;
    private CaregiverRoleDTO caregiverRoleDTO;
    private CaregiverScheduleDTO caregiverScheduleDTO;
    private CaregiverSpecialityDTO specialityDTO;
}
