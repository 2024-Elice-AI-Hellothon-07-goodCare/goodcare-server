package com.goodcare.server.patient.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@RequiredArgsConstructor
public class PatientDTO {
    private Long id;
    private String code;
    private String name;
    private LocalDate dateOfBirth;
    private String gender;
    private String contactNumber;
}
