package com.goodcare.server.domain.patient.repository.patientinfo;

import com.goodcare.server.domain.patient.dao.patientinfo.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    public Optional<Patient> findByCode(String code);

    @Query("select p from Patient p where p.name = :name")
    public List<Patient> findPatientsByName(@Param("name") String name);

    @Query("select p from Patient p where p.code = :code and p.name = :name")
    public Optional<Patient> findPatientByCodeAndName(@Param("code") String code, @Param("name") String name);
}
