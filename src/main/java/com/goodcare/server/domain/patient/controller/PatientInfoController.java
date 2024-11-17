package com.goodcare.server.domain.patient.controller;

import com.goodcare.server.domain.patient.dto.PatientInfoDTO;
import com.goodcare.server.domain.patient.service.PatientInfoService;
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
@RequestMapping("/patient/info")
@Tag(name = "Patient_INFO", description = "Patient DB 관련 api")
@AllArgsConstructor
public class PatientInfoController {

    private final PatientInfoService patientInfoService;

    @PostMapping("/register")
    @Operation(
            summary = "환자 정보 등록 API",
            description = "환자의 정보를 받아 DB에 등록합니다."
    )
    public ApiResponse<?> registerPatient(@RequestBody PatientInfoDTO patientInfoDTO){
        String result = patientInfoService.registerPatient(patientInfoDTO);

        return ApiResponse.onSuccess(Status.OK.getCode(), Status.OK.getMessage(), result);
    }
}
