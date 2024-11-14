package com.goodcare.server.domain.patient.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EmergencyContactDTO {
    private String emergencyName;
    private String emergencyContact;
    private String relationship;
}
