package com.goodcare.server.patient.repository;

import com.goodcare.server.patient.dao.HealthInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HealthInfoRepository extends JpaRepository<HealthInfo, Long> {
}
