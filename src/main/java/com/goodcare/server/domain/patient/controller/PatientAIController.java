package com.goodcare.server.domain.patient.controller;

import com.goodcare.server.domain.patient.dao.PatientFile;
import com.goodcare.server.domain.patient.service.PatientAIService;
import com.goodcare.server.global.response.ApiResponse;
import com.goodcare.server.global.response.Status;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

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
    public ApiResponse<String> CreateChat(@RequestParam("text") String text) throws Exception {
        String answer = patientAIService.getChat(text);
        return ApiResponse.onSuccess(Status.OK.getCode(), Status.OK.getMessage(), answer);
    }

    @GetMapping("/generate-speech")
    @Operation(
            summary = "IntraSpeech 생성 API",
            description = "환자 코드로 저장된 오디오 파일을 이용해 주어진 텍스트를 기반으로 IntraSpeech를 생성합니다."
    )
    public ApiResponse<?> generateSpeech(
            @RequestParam("code") String code,
            @RequestParam("text") String text,
            HttpServletResponse response
    ) throws Exception {
        // getIntraSpeechFromPatientFile 메서드 호출
        byte[] audioResponse = patientAIService.getIntraSpeechFromPatientFile(code, text);

        // HTTP 응답 헤더 설정
        response.setContentType("audio/wav");  // 반환된 데이터 형식에 따라 MIME 타입 설정
        response.setHeader("Content-Disposition", "attachment; filename=\"generated_speech.wav\"");
        response.setContentLength(audioResponse.length);

        // 스트리밍 데이터 전송
        try (OutputStream out = response.getOutputStream()) {
            out.write(audioResponse);  // audioBytes를 응답으로 전송
            out.flush();
        }

        // 성공적인 응답 처리
        return ApiResponse.onSuccess(Status.OK.getCode(), Status.OK.getMessage(), "WAV file generated successfully.");
    }
}
