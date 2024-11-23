package com.goodcare.server.domain.caregiver.controller;

import com.goodcare.server.domain.caregiver.dto.CaregiverDTOBundle;
import com.goodcare.server.domain.guardian.dto.GuardianDTOBundle;
import com.goodcare.server.domain.caregiver.service.CaregiverInfoService;
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
@RequestMapping("/caregiver/info")
@Tag(name = "Caregiver_Info", description = "간병인 DB 관련 api")
@AllArgsConstructor
public class CaregiverInfoController {

    private final CaregiverInfoService caregiverInfoService;

    @PostMapping("/save")
    @Operation(
            summary = "간병인 정보 가입 API",
            description = "간병인 회원가입시 정보를 받아 DB에 저장합니다."
    )
    public ApiResponse<?> saveCaregiver(@RequestBody CaregiverDTOBundle caregiverDTOBundle)
    {
        Boolean result = caregiverInfoService.saveCaregiver(caregiverDTOBundle);
        
        if(!result)
            return ApiResponse.onFailure(Status.CONFLICT.getCode(), Status.CONFLICT.getMessage(), false);
        else
            return ApiResponse.onSuccess(Status.OK.getCode(), Status.OK.getMessage(), true);
    }
}
