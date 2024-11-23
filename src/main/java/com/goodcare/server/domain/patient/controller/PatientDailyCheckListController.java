package com.goodcare.server.domain.patient.controller;

import com.goodcare.server.domain.patient.dao.DailyCheckListBundle;
import com.goodcare.server.domain.patient.dao.dailychecklist.DailyCheckList;
import com.goodcare.server.domain.patient.dao.dailychecklist.VitalSigns;
import com.goodcare.server.domain.patient.dto.PatientDailyCheckListDTOBundle;
import com.goodcare.server.domain.patient.dto.dailychecklistdto.VitalSignsDTO;
import com.goodcare.server.domain.patient.service.PatientAIService;
import com.goodcare.server.domain.patient.service.PatientDailyCheckListService;
import com.goodcare.server.global.response.ApiResponse;
import com.goodcare.server.global.response.Status;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;

@RestController
@RequestMapping("/patient/daily-check")
@AllArgsConstructor
@Tag(name = "Patient_Daily_Checklist", description = "환자 일일 건강 상태 체크리스트 관련 api")
public class PatientDailyCheckListController {

    private final PatientDailyCheckListService patientDailyCheckListService;
    private final PatientAIService patientAIService;

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

    @GetMapping("/get/checklist")
    @Operation(
            summary = "환자 일일 건강 상태 체크리스트 반환 api",
            description = "특정 날짜의 체크리스트를 반환합니다"
    )
    public ApiResponse<?> hasTodayChecklist(
            @RequestParam("date") LocalDate date,
            @RequestParam("code") String code
    ){
        DailyCheckListBundle bundle = patientDailyCheckListService.getDateDailyCheckListBundle(date, code);
        if(bundle == null)
            return ApiResponse.onFailure(Status.CONFLICT.getCode(), Status.CONFLICT.getMessage(), null);
        else
            return ApiResponse.onSuccess(Status.OK.getCode(), Status.CONFLICT.getMessage(), bundle);
    }

    @GetMapping("/get/vital-signs")
    @Operation(
            summary = "환자 일일 vital_signs 체크 api",
            description = "환자의 특정 날짜 체크리스트를 참고하여 건강 수치를 반환합니다."
    )
    public ApiResponse<?> getVitalSigns(
            @RequestParam("date") LocalDate date,
            @RequestParam("code") String code
    ){
        DailyCheckListBundle dailyCheckListBundle = patientDailyCheckListService
                .getDateDailyCheckListBundle(date, code);

        VitalSigns vitalSigns = dailyCheckListBundle.getVitalSigns();
        if(vitalSigns == null){
            return ApiResponse.onFailure(Status.ANALYZED_FILE_NOT_FOUND.getCode(),
                    Status.ANALYZED_FILE_NOT_FOUND.getMessage(), null);
        }else{
            return ApiResponse.onSuccess(Status.OK.getCode(), Status.OK.getMessage(), vitalSigns);
        }
    }

    @GetMapping("/get/ai-analysis")
    @Operation(
            summary = "인공지능 건강 분석 API",
            description = "인공지능이 분석한 오늘의 건강 분석 결과를 반환합니다."
    )
    public ApiResponse<?> getAiAnalysis(
            @RequestParam("date") LocalDate date,
            @RequestParam("code") String code
    ) throws IOException, InterruptedException {
        String dailyCheckListString = patientDailyCheckListService
                .aiAnalysisOnSentenceDailyCheckList(code, date);

        String analysisData = patientAIService.getChat(
                dailyCheckListString +
                        "이 환자의 건강상태 확인표를 보고 환자의 건강 상태를 1줄로 말해줘."
        );

        String statusData = patientAIService.getChat(
                dailyCheckListString +
                        "이 환자의 건강상태 확인표를 보고 환자의 건강 상태를 (나쁨, 보통, 좋음)" +
                        "이 3단어 중 1단어만 뽑아서 보여줘. 다른 말 붙이지 말고 저 단어 1개만."
        );

        String[] listData = {analysisData, statusData};

        DailyCheckListBundle bundle = patientDailyCheckListService.
                getDateDailyCheckListBundle(date, code);

        DailyCheckList dailyCheckList = bundle.getDailyCheckList();
        dailyCheckList.setAnalysisData(analysisData);
        dailyCheckList.setAnalysisWord(statusData);

        int dataUpdated = patientDailyCheckListService.setAnalysisData(dailyCheckList);
        int wordUpdated = patientDailyCheckListService.setAnalysisWord(dailyCheckList);
        if (dataUpdated > 0 && wordUpdated > 0) {
            return ApiResponse.onSuccess(Status.OK.getCode(), Status.OK.getMessage(), listData);
        } else {
            return ApiResponse.onFailure(Status.INTERNAL_SERVER_ERROR.getCode(),
                    Status.INTERNAL_SERVER_ERROR.getMessage(), "응답 생성에 실패하였습니다.");
        }

    }
}
