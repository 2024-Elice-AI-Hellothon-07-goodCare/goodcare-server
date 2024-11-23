package com.goodcare.server.domain.patient.dao.dailychecklist;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name="checklist")
public class DailyCheckList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="checklist_code", nullable = false)
    private String code;

    @Column(name="patient_code", nullable = false)
    private String patientCode;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDate createdAt;

    @Column(name = "analysis_data", columnDefinition = "TEXT")
    private String analysisData;

    @Column(name = "analysis_word")
    private String analysisWord;

    @Column(name = "analysis_full_data", columnDefinition = "TEXT")
    private String analysisFullData;

    @Override
    public String toString() {
        return "DailyCheckList{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", patientCode='" + patientCode + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }

}
