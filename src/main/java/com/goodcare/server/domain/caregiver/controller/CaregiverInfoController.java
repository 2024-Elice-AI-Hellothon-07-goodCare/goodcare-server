package com.goodcare.server.domain.caregiver.controller;

import com.goodcare.server.domain.caregiver.dto.CaregiverDTOBundle;
import com.goodcare.server.domain.guardian.dto.GuardianDTOBundle;
import com.goodcare.server.domain.caregiver.service.CaregiverInfoService;
import com.goodcare.server.global.response.ApiResponse;
import com.goodcare.server.global.response.Status;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/get/patient-name")
    @Operation(
            summary = "환자 이름을 얻어옵니다",
            description = "간병인 코드를 이용해 환자 이름을 얻어옵니다."
    )
    public ApiResponse<?> getPatientName(String code){
        String name = caregiverInfoService.getPatientNameByCaregiverCode(code);
        if(name == null){
            return ApiResponse.onSuccess(Status.ANALYZED_FILE_NOT_FOUND.getCode(), Status.ANALYZED_FILE_NOT_FOUND.getMessage(), null);
        }else{
            return ApiResponse.onSuccess(Status.OK.getCode(), Status.OK.getMessage(), name);
        }
    }
}
