package com.goodcare.server.domain.guardian.controller;

import com.goodcare.server.domain.guardian.dto.GuardianDTO;
import com.goodcare.server.domain.guardian.service.GuardianInfoService;
import com.goodcare.server.global.response.ApiResponse;
import com.goodcare.server.global.response.Status;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/guardian/info")
@AllArgsConstructor
public class GuardianInfoController {
    private final GuardianInfoService guardianInfoService;

    @PostMapping("/save")
    @Operation(
            summary = "보호자 회원가입",
            description = "보호자 정보를 받아 회원가입 합니다."
    )
    public ApiResponse<?> registerGuardian(@RequestBody GuardianDTO guardianDTO) {
        Boolean result = guardianInfoService.saveGuardian(guardianDTO);

        if(!result)
            return ApiResponse.onFailure(Status.INTERNAL_SERVER_ERROR.getCode(),
                    Status.INTERNAL_SERVER_ERROR.getMessage(), result);
        else
            return ApiResponse.onSuccess(Status.OK.getCode(), Status.OK.getMessage(), result);
    }
}
