package com.goodcare.server.domain.patient.dto.dailychecklistdto;

import com.goodcare.server.domain.patient.dao.dailychecklist.enums.ConsciousnessLevel;
import com.goodcare.server.domain.patient.dao.dailychecklist.enums.MoodBehaviour;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConsciousnessDTO {
    private ConsciousnessLevel consciousnessLevel;
    private MoodBehaviour moodBehaviour;
}
