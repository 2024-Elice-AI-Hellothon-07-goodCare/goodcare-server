package com.goodcare.server.domain.guardian.controller;

import com.goodcare.server.domain.guardian.dao.routine.GuardianRoutine;
import com.goodcare.server.domain.guardian.dto.GuardianRoutineDTOBundle;
import com.goodcare.server.domain.guardian.service.GuardianRoutineService;
import com.goodcare.server.global.response.ApiResponse;
import com.goodcare.server.global.response.Status;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/guardian/routine")
@AllArgsConstructor
@Tag(name = "Guardian_Routine", description = "루틴 저장 API")
public class GuardianRoutineController {

    private final GuardianRoutineService guardianRoutineService;

    @PostMapping("/save")
    @Operation(
            summary = "루틴 저장 API",
            description = "환자의 루틴을 간병인이 저장해서 DB에 저장합니다."
    )public ApiResponse<?> saveGuardianRoutine(@RequestBody GuardianRoutineDTOBundle guardianRoutineDTOBundle) {
        Boolean result = guardianRoutineService.saveRoutine(guardianRoutineDTOBundle);
        return result ?
                ApiResponse.onSuccess(Status.OK.getCode(), Status.OK.getMessage(), true) :
                ApiResponse.onFailure(Status.CONFLICT.getCode(), Status.CONFLICT.getMessage(), false);
    }
}
