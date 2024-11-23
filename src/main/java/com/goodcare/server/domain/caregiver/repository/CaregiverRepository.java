package com.goodcare.server.domain.caregiver.repository;

import com.goodcare.server.domain.caregiver.dao.Caregiver;
import com.goodcare.server.domain.guardian.dao.Guardian;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CaregiverRepository extends JpaRepository<Caregiver, Long> {
    @Query("select c from Caregiver c where c.code = :code")
    public Optional<Caregiver> findCaregiverByCode(@Param("code") String code);
}
