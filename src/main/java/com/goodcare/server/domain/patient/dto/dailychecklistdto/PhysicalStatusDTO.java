package com.goodcare.server.domain.patient.dto.dailychecklistdto;

import com.goodcare.server.domain.patient.dao.dailychecklist.enums.Mobility;
import com.goodcare.server.domain.patient.dao.dailychecklist.enums.PainLevel;
import com.goodcare.server.domain.patient.dao.dailychecklist.enums.SkinCondition;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PhysicalStatusDTO {
    private SkinCondition skinCondition;
    private PainLevel painLevel;
    private String painLocation;
    private Mobility mobility;
}
