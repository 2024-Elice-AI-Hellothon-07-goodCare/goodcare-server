package com.goodcare.server.domain.patient.dto.dailychecklistdto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MedicationsDTO {
    private Boolean medicationTaken;
    private String sideEffects;
}