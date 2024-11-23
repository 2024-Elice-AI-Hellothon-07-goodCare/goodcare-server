package com.goodcare.server.domain.patient.controller;

import com.goodcare.server.domain.patient.dao.patientinfo.InterSpeechFile;
import com.goodcare.server.domain.patient.service.PatientAIService;
import com.goodcare.server.domain.patient.service.PatientFileService;
import com.goodcare.server.global.response.ApiResponse;
import com.goodcare.server.global.response.Status;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.util.AlternativeJdkIdGenerator;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Tag(name = "Patient_AI", description = "Patient AI 관련 API")
@RestController
@RequestMapping("/patient/ai")
@AllArgsConstructor
public class PatientAIController {
    private final PatientAIService patientAIService;
    private final PatientFileService patientFileService;
    private String getUUID(){
        UUID uuid = new AlternativeJdkIdGenerator().generateId();
        return uuid.toString();
    }
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

        // 파일 저장 경로 설정 (서버 저장소)
        String uploadDir = Paths.get(System.getProperty("user.dir"), "file", code).toString();
        File directory = new File(uploadDir);

        // 디렉토리가 존재하지 않으면 생성
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // 저장할 파일의 전체 경로
        String filePath = uploadDir + File.separator + code + "_" + getUUID() + "_" + ".wav";
        File file = new File(filePath);
        String filename = file.getName();
        // 서버에 파일 저장
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(audioResponse);
            fos.flush();
        }
        Boolean result = patientFileService.inputInterSpeech(filePath, filename, code, text);
//        // HTTP 응답 헤더 설정
//        response.setContentType("audio/wav");  // 반환된 데이터 형식에 따라 MIME 타입 설정
//        response.setHeader("Content-Disposition", "attachment; filename=\"generated_speech.wav\"");
//        response.setContentLength(audioResponse.length);
//
//        // 스트리밍 데이터 전송
//        try (OutputStream out = response.getOutputStream()) {
//            out.write(audioResponse);  // audioBytes를 응답으로 전송
//            out.flush();
//        }

        // 성공적인 응답 처리
        return ApiResponse.onSuccess(Status.OK.getCode(), Status.OK.getMessage(),
                "WAV file generated and saved successfully: " + filePath);
    }

    @GetMapping("/get/intra-speech")
    @Operation(
            summary = "IntraSpeech 반환 API",
            description = "DB내 정보 반환"
    )
    public ApiResponse<?> generateSpeech(@RequestParam String code){
        List<InterSpeechFile> interSpeechFileList = patientFileService.getIntraSpeechList(code);

        return ApiResponse.onSuccess(Status.OK.getCode(), Status.OK.getMessage(), interSpeechFileList);
    }
}
