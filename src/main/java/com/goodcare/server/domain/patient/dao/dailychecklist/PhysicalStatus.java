package com.goodcare.server.domain.patient.dao.dailychecklist;

import com.goodcare.server.domain.patient.dao.dailychecklist.enums.Mobility;
import com.goodcare.server.domain.patient.dao.dailychecklist.enums.PainLevel;
import com.goodcare.server.domain.patient.dao.dailychecklist.enums.SkinCondition;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="checklist_physical_status")
public class PhysicalStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="checkllist_code", nullable = false)
    private String checkListCode;

    @Column(name="skin_condition", nullable = false)
    private SkinCondition skinCondition;

    @Column(name = "pain_level", nullable=false)
    private PainLevel painLevel;


    @Column(name="mobility", nullable = false)
    private Mobility mobility;

    @Override
    public String toString() {
        return "PhysicalStatus{" +
                "id=" + id +
                ", checkListCode='" + checkListCode + '\'' +
                ", skinCondition=" + skinCondition +
                ", painLevel=" + painLevel +
                ", mobility=" + mobility +
                '}';
    }

}
