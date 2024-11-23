package com.goodcare.server.domain.patient.dto.dailychecklistdto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VitalSignsDTO {
    private Double temperature;         // 체온
    private Double bloodPressureSys;    // 수축기 혈압
    private Double bloodPressureDia;    //  이완기 혈압
    private Integer pulse;  // 맥박
    private Integer oxygen; // 산소 포화도
    private Integer respirationRate;    // 호흠수
}
