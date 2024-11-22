package com.goodcare.server.domain.patient.repository.patientinfo;

import com.goodcare.server.domain.patient.dao.patientinfo.HealthInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HealthInfoRepository extends JpaRepository<HealthInfo, Long> {
    public Optional<HealthInfo> findHealthInfoByPatientCode(String code);
}
