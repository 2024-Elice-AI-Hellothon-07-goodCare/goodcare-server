package com.goodcare.server.domain.patient.dao.patientinfo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name="patient_interspeech_")
public class InterSpeechFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="patient_id")
    private Long id;

    @Column(name="patient_code")
    private String code;

    @Column(name="interspeech_file_name", nullable = false)
    private String interSpeechFileName;

    @Column(name="interspeech_file_path")
    private String interSpeechFilePath;

    @Column(name="input_text")
    private String inputText;
}
