package com.goodcare.server.domain.patient.dao;

import com.goodcare.server.domain.patient.dao.dailychecklist.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DailyCheckListBundle {
    private PhysicalStatus physicalStatus;
    private DailyCheckList dailyCheckList;
    private VitalSigns vitalSigns;
    private Consciousness consciousness;
    private Medications medications;
    private SpecialNotes specialNotes;
}
