package com.goodcare.server.domain.caregiver.controller;

import com.goodcare.server.domain.caregiver.dao.CaregiverRoutineBundle;
import com.goodcare.server.domain.caregiver.dto.CaregiverRoutineDTOBundle;
import com.goodcare.server.domain.caregiver.dto.SearchRoutineDTO;
import com.goodcare.server.domain.caregiver.service.CaregiverRoutineService;
import com.goodcare.server.global.response.ApiResponse;
import com.goodcare.server.global.response.Status;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/caregiver/routine")
@AllArgsConstructor
@Tag(name = "Caregiver_Routine", description = "루틴 저장 API")
public class CaregiverRoutineController {

    private final CaregiverRoutineService caregiverRoutineService;

    @PostMapping("/save")
    @Operation(
            summary = "루틴 저장 API",
            description = "환자의 루틴을 간병인이 저장해서 DB에 저장합니다."
    )public ApiResponse<?> saveGuardianRoutine(@RequestBody CaregiverRoutineDTOBundle caregiverRoutineDTOBundle) {
        Boolean result = caregiverRoutineService.saveRoutine(caregiverRoutineDTOBundle);
        return result ?
                ApiResponse.onSuccess(Status.OK.getCode(), Status.OK.getMessage(), true) :
                ApiResponse.onFailure(Status.CONFLICT.getCode(), Status.CONFLICT.getMessage(), false);
    }

    @GetMapping("/get")
    @Operation(
            summary = "루틴 조회 API",
            description="환자 코드와 오늘 날짜를 활용하여 등록된 루틴을 조회합니다."
    )public ApiResponse<?> getRoutineList(
            @RequestParam("code")String patientCode
    ){
        List<SearchRoutineDTO> result = caregiverRoutineService.searchRoutine(patientCode);

        return ApiResponse.onSuccess(Status.OK.getCode(), Status.OK.getMessage(), result);
    }
}
