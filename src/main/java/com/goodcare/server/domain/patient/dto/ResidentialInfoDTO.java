package com.goodcare.server.domain.patient.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResidentialInfoDTO {
    private String patientCode;
    private String residence;
}
