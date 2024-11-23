package com.goodcare.server.domain.patient.dao.dailychecklist;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "checklist_special_notes")
public class SpecialNotes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="checklist_code", nullable = false)
    private String checkListCode;

    @Column(name="special_notes", columnDefinition = "TEXT")
    private String specialNotes;

    @Column(name="caregiver_notes", columnDefinition = "TEXT")
    private String caregiverNotes;
}
