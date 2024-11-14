package com.goodcare.server.domain.patient.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class PatientDTO {
    private String name;
    private LocalDate dateOfBirth;
    private String gender;
    private String contactNumber;
}
