package com.goodcare.server.domain.patient.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goodcare.server.domain.patient.dao.patientinfo.PatientFile;
import com.goodcare.server.domain.patient.repository.PatientRepositoryBundle;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.AlternativeJdkIdGenerator;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Service
@Getter
@RequiredArgsConstructor
public class PatientAIService {
    @Value("${api.elice.ml-api-key}")
    private String mlApiKey;

    private final PatientRepositoryBundle patientRepositoryBundle;

    private String getUUID(){
        UUID uuid = new AlternativeJdkIdGenerator().generateId();
        return uuid.toString();
    }

    public String getChat(String text) throws IOException, InterruptedException {
        String jsonBody = String.format(
                "{\"model\":\"helpy-pro\",\"sess_id\":\"%s\",\"messages\":[{\"role\":\"user\",\"content\":\"%s\"}],\"max_tokens\":512}"
                ,getUUID(), text);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api-cloud-function.elice.io/5a327f26-cc55-45c5-92b7-e909c2df0ba4/v1/chat/completions"))
                .header("accept", "application/json")
                .header("content-type", "multipart/form-data")
                .header("authorization", "Bearer " + getMlApiKey())
                .method("POST", HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        // content만 뽑기
        // Jackson ObjectMapper 초기화
        ObjectMapper objectMapper = new ObjectMapper();

        // JSON 문자열 파싱
        JsonNode rootNode = objectMapper.readTree(response.body());

        return rootNode
                .path("choices")  // "choices" 배열
                .get(0)           // 배열의 첫 번째 요소
                .path("message")  // "message" 객체
                .path("content")  // "content" 필드
                .asText();
    }

    public byte[] getIntraSpeechFromPatientFile(String code, String text) throws IOException, InterruptedException {
        // PatientFile 정보를 가져오기
        PatientFile patientFile = patientRepositoryBundle.getPatientFileRepository()
                .findByCode(code).orElseThrow(() -> new FileNotFoundException("Patient file not found for code: " + code));

        Path filePath = Path.of(patientFile.getFilePath());
        String filename = patientFile.getFileName();

        System.out.println("File path: " + filePath);
        System.out.println("File name: " + filename);

        if (!Files.exists(filePath)) {
            throw new FileNotFoundException("File not found at path: " + filePath);
        }

        // Boundary 생성
        String boundary = "----Boundary" + UUID.randomUUID();

        // HTTP 요청 본문 구성
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(byteArrayOutputStream, StandardCharsets.UTF_8))) {
            // 오디오 파일 추가
            writer.append("--").append(boundary).append("\r\n");
            writer.append("Content-Disposition: form-data; name=\"audio\"; filename=\"").append(filename).append("\"\r\n");
            writer.append("Content-Type: audio/mpeg\r\n\r\n");
            writer.flush();
            byteArrayOutputStream.write(Files.readAllBytes(filePath));
            writer.append("\r\n").flush();

            // 텍스트 필드 추가
            writer.append("--").append(boundary).append("\r\n");
            writer.append("Content-Disposition: form-data; name=\"text\"\r\n\r\n");
            writer.append(text).append("\r\n");
            writer.flush();

            // 최종 Boundary
            writer.append("--").append(boundary).append("--").append("\r\n");
            writer.flush();
        }

        // HTTP 요청 생성
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api-cloud-function.elice.io/fb0764c6-4aff-4e22-b93f-e51552fefde0/generate"))
                .header("Authorization", "Bearer " + getMlApiKey())
                .header("Content-Type", "multipart/form-data; boundary=" + boundary)
                .POST(HttpRequest.BodyPublishers.ofByteArray(byteArrayOutputStream.toByteArray()))
                .build();

        // HTTP 요청 전송 및 응답 처리 (바이트 배열로 응답 받음)
        HttpResponse<byte[]> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofByteArray());

        // 응답 출력 (디버깅용)
        System.out.println("Response Code: " + response.statusCode());

        // 에러 처리
        if (response.statusCode() == 415) {
            System.err.println("415 Unsupported Media Type - Check Content-Type or body structure");
        }

        // 응답 본문을 바이트 배열로 반환
        return response.body();  // 바이트 배열 반환
    }
}
