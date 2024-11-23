package com.goodcare.server.domain.guardian.repository;

import com.goodcare.server.domain.guardian.dao.Guardian;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GuardianRepository extends JpaRepository<Guardian, Long> {
    @Query("select g from Guardian g where g.code = :code")
    public Optional<Guardian> findGuardianByCode(@Param("code") String code);
}
