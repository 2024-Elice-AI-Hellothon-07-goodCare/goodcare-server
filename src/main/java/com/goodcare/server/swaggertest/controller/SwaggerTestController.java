package com.goodcare.server.swaggertest.controller;

import com.goodcare.server.global.response.ApiResponse;
import com.goodcare.server.global.response.Status;
import com.goodcare.server.swaggertest.dto.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Swagger test", description = "test api")
@RestController
@RequestMapping("/")
public class SwaggerTestController {

    @GetMapping
    @Operation(summary = "Swagger 동작 관련 테스트 api")
    public String index() {
        return "Hello World";
    }

    @GetMapping("/test")
    @Operation(summary = "ApiResponse관련 테스트 api")
    public ApiResponse<?> getUserTest() {
        // 예시로 UserDto 객체 생성
        UserDto user = new UserDto(1L, "testuser");
        return ApiResponse.onSuccess(Status.OK.getCode(), Status.OK.getMessage(), user);
    }
}
