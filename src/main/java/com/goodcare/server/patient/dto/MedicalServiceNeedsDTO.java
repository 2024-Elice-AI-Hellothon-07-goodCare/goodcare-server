package com.goodcare.server.patient.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class MedicalServiceNeedsDTO {
    private Long id;
    private String patinetCode;
    private Boolean regularCheckups;
    private String treatments;
    private Boolean specialNursing;
}
