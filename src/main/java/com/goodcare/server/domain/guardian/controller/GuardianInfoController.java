package com.goodcare.server.domain.guardian.controller;

import com.goodcare.server.domain.guardian.dto.GuardianDTOBundle;
import com.goodcare.server.domain.guardian.service.GuardianInfoService;
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
@RequestMapping("/guardian/info")
@Tag(name = "Guardian_Info", description = "간병인 DB 관련 api")
@AllArgsConstructor
public class GuardianInfoController {

    private final GuardianInfoService guardianInfoService;

    @PostMapping("/save")
    @Operation(
            summary = "간병인 정보 가임 API",
            description = "간병인 회원가입시 정보를 받아 DB에 저장합니다."
    )
    public ApiResponse<?> saveGuadian(@RequestBody GuardianDTOBundle guardianDTOBundle)
    {
        Boolean result = guardianInfoService.saveGuardian(guardianDTOBundle);
        
        if(!result)
            return ApiResponse.onFailure(Status.CONFLICT.getCode(), Status.CONFLICT.getMessage(), false);
        else
            return ApiResponse.onSuccess(Status.OK.getCode(), Status.OK.getMessage(), true);
    }
}
