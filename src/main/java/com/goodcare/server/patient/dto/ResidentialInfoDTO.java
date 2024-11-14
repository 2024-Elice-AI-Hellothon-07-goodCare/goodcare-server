package com.goodcare.server.patient.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ResidentialInfoDTO {
    private Long id;
    private String patientCode;
    private String residence;
}
