package com.goodcare.server.patient.dao;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "medical_service_needs")
public class MedicalServiceNeeds {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "patinet_code", nullable = false)
    private String patinetCode;

    @Column(name = "regular_checkups")
    private Boolean regularCheckups;

    private String treatments;

    @Column(name = "special_nursing")
    private Boolean specialNursing;
}
