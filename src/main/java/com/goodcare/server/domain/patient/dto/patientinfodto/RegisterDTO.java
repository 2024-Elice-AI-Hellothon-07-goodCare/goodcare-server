package com.goodcare.server.domain.patient.dto.patientinfodto;

import com.goodcare.server.domain.patient.dao.patientinfo.Patient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDTO{
    private Patient patient;
    private Boolean success;
}
