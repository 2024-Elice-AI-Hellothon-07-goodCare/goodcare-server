package com.goodcare.server.domain.patient.controller;

import com.goodcare.server.domain.patient.service.PatientAIService;
import com.goodcare.server.global.response.ApiResponse;
import com.goodcare.server.global.response.Status;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.http.HttpResponse;

@Tag(name = "Patient_AI", description = "Patient AI 관련 API")
@RestController
@RequestMapping("/patient/ai")
@AllArgsConstructor
public class PatientAIController {
    private final PatientAIService patientAIService;
    
    @GetMapping(value = "/chat")
    @Operation(
            summary = "챗봇 creation 관련 test api",
            description = "주어진 문장에 대한 답을 create 합니다"
    )
    public ApiResponse<String> CreateChat(@RequestParam String text) throws IOException, InterruptedException {
        String answer = patientAIService.getChat(text);
        return ApiResponse.onSuccess(Status.OK.getCode(), Status.OK.getMessage(), answer);
    }
}
