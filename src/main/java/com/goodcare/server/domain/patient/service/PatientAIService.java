package com.goodcare.server.domain.patient.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.AlternativeJdkIdGenerator;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.UUID;

@Service
@Getter
public class PatientAIService {
    @Value("${api.elice.ml-api-key}")
    private String mlApiKey;

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
                .header("content-type", "application/json")
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
}
