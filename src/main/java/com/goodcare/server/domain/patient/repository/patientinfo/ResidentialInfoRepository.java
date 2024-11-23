package com.goodcare.server.domain.patient.repository.patientinfo;

import com.goodcare.server.domain.patient.dao.patientinfo.ResidentialInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResidentialInfoRepository extends JpaRepository<ResidentialInfo, Long> {
    public Optional<ResidentialInfo> findResidentialInfoByPatientCode(String code);
}
