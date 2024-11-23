package com.goodcare.server.domain.patient.repository.patientinfo;

import com.goodcare.server.domain.patient.dao.patientinfo.InterSpeechFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InterSpeechFileRepository extends JpaRepository<InterSpeechFile, Long> {
    public Optional<InterSpeechFile> findInterSpeechFileByCode(String code);

    @Query("select i from InterSpeechFile i where i.code =:code")
    public List<InterSpeechFile> findInterSpeechFilesByCode(@Param("code") String code);
}
