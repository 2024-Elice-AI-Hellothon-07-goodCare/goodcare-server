package com.goodcare.server.domain.patient.dto;
import com.goodcare.server.domain.patient.dto.dailychecklistdto.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PatientDailyCheckListDTOBundle {
    private DailyCheckListDTO dailyCheckListDTO;
    private VitalSignsDTO vitalSignsDTO;
    private ConsciousnessDTO consciousnessDTO;
    private PhysicalStatusDTO physicalStatusDTO;
    private MedicationsDTO medicationsDTO;
    private SpecialNotesDTO specialNotesDTO;
}
