package com.goodcare.server.patient.repository;

import com.goodcare.server.patient.dao.ResidentialInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResidentialInfoRepository extends JpaRepository<ResidentialInfo, Long> {
}
