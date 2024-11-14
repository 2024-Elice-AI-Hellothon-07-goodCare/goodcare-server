package com.goodcare.server.patient.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class HealthInfoDTO {
    private Long Id;
    private String patientCode;
    private String diseaseHistory;
    private String medications;
    private String allergies;
}
