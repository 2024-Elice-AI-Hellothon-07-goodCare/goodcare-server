package com.goodcare.server.domain.guardian.dto;

import com.goodcare.server.domain.guardian.dto.guardian.GuardianConsentDTO;
import com.goodcare.server.domain.guardian.dto.guardian.GuardianContactPreferenceDTO;
import com.goodcare.server.domain.guardian.dto.guardian.GuardianDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GuardianDTOBundle {
    private GuardianDTO guardianDTO;
    private GuardianConsentDTO guardianConsentDTO;
    private GuardianContactPreferenceDTO guardianContactPreferenceDTO;
}
