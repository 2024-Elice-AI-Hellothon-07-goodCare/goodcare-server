package com.goodcare.server.domain.patient.repository.dailychecklist;

import com.goodcare.server.domain.patient.dao.dailychecklist.DailyCheckList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface DailyCheckListRepository extends JpaRepository<DailyCheckList, Long> {
    @Query("select d from DailyCheckList d where d.createdAt = :today and d.patientCode = :code")
    public Optional<DailyCheckList> findDailyCheckListByTodayAndPatientCode(
            @Param("today") LocalDate date,
            @Param("code") String code
    );

    @Modifying
    @Query("update DailyCheckList d set d.analysisData = :data where d.code = :code")
    int updateDailyCheckListByAnalysisData(@Param("data") String data, @Param("code") String code);
}
