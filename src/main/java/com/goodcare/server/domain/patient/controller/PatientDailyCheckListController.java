package com.goodcare.server.domain.patient.controller;

import com.goodcare.server.domain.patient.dto.PatientDailyCheckListDTOBundle;
import com.goodcare.server.domain.patient.service.PatientDailyCheckListService;
import com.goodcare.server.global.response.ApiResponse;
import com.goodcare.server.global.response.Status;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/patient/daily-check")
@AllArgsConstructor
@Tag(name = "Patient_Daily_Checklist", description = "환자 일일 건강 상태 체크리스트 관련 api")
public class PatientDailyCheckListController {

    private final PatientDailyCheckListService patientDailyCheckListService;

    @PostMapping("/input")
    @Operation(
            summary = "환자 일일 건강 상태 체크리스트 삽입 api",
            description = "환자 일일 건강 상태 체크리스트 완료 후 db에 저장합니다."
    )
    public ApiResponse<?> inputDailyCheckList(
            @RequestBody PatientDailyCheckListDTOBundle patientDailyCheckListDTOBundle,
            @RequestParam("code") String patientCode
    ){

        Boolean result = patientDailyCheckListService.saveDailyCheckList(patientDailyCheckListDTOBundle, patientCode);
        if(result)
            return ApiResponse.onSuccess(Status.OK.getCode(), Status.OK.getMessage(),
                    LocalDate.now() + "일자 체크리스트 입력 성공!");
        else
            return ApiResponse.onSuccess(Status.CONFLICT.getCode(), Status.CONFLICT.getMessage(), LocalDate.now() + "일자 입력에 실패하였습니다.");
    }

    @GetMapping("/check-today")
    @Operation(
            summary = "환자 일일 건강 상태 체크리스트 오늘 일자 체크 api",
            description = "환자 일일 건강 상태 체크리스트를 입력하기전 오늘 날짜의 체크리스트가 있는지 확인합니다."
    )
    public ApiResponse<?> hasTodayChecklist(
            @RequestParam("code") String code
    ){
        Boolean isHas = patientDailyCheckListService.isChecklistCreatedToday(LocalDate.now(), code);
        if(isHas)
            return ApiResponse.onFailure(Status.CONFLICT.getCode(), Status.CONFLICT.getMessage(), false);
        else
            return ApiResponse.onSuccess(Status.OK.getCode(), Status.CONFLICT.getMessage(), true);
    }
}
