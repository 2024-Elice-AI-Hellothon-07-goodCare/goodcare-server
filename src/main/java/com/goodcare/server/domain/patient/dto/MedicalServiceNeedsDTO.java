package com.goodcare.server.domain.patient.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MedicalServiceNeedsDTO {
    private Boolean regularCheckups;
    private String treatments;
    private Boolean specialNursing;
}
