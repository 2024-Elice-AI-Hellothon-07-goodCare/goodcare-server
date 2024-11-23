package com.goodcare.server.domain.patient.controller;

import com.goodcare.server.domain.patient.dao.PatientDAOBundle;
import com.goodcare.server.domain.patient.dto.PatientInfoDTO;
import com.goodcare.server.domain.patient.service.PatientInfoService;
import com.goodcare.server.global.response.ApiResponse;
import com.goodcare.server.global.response.Status;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patient/info")
@Tag(name = "Patient_Info", description = "Patient DB 관련 api")
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

    @GetMapping("/get/code")
    @Operation(
            summary = "환자 정보 검색 API(환자 코드 사용)",
            description = "환자 코드를 활용하여 환자의 모든 정보를 DB에서 받아옵니다."
    )
    public ApiResponse<?> getPatientInfoByCode(@RequestParam("code") String code){
        PatientDAOBundle result = patientInfoService.searchPatientByCode(code);
        return ApiResponse.onSuccess(Status.OK.getCode(), Status.OK.getMessage(), result);
    }

    @GetMapping("/get/name  ")
    @Operation(
            summary = "환자 정보 검색 API(환자 이름 사용)",
            description = "환자 이름을 활용하여 DB내 환자 정보를 받아옵니다(다수)"
    )
    public ApiResponse<?> getPatientInfosByName(@RequestParam("name") String name){
        List<PatientDAOBundle> result = patientInfoService.searchPatientsByName(name);
        return ApiResponse.onSuccess(Status.OK.getCode(), Status.OK.getMessage(), result);
    }
}
