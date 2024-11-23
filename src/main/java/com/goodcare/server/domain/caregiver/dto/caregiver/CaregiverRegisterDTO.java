package com.goodcare.server.domain.caregiver.dto.caregiver;

import com.goodcare.server.domain.caregiver.dao.Caregiver;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CaregiverRegisterDTO {
    private Caregiver caregiver;
    private Boolean success;
}
