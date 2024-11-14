package com.goodcare.server.domain.patient.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class HealthInfoDTO {
    private String diseaseHistory;
    private String medications;
    private String allergies;
}
