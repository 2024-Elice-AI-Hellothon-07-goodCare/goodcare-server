package com.goodcare.server.file.controller;

import com.goodcare.server.file.dao.FileDAO;
import com.goodcare.server.file.service.FileService;
import com.goodcare.server.global.response.ApiResponse;
import com.goodcare.server.global.response.Status;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileController {

    @Autowired
    private final FileService fileService;

    @PostMapping(value =  "/upload", consumes = "multipart/form-data")
    @Operation(
            summary = "파일 업로드 관련 테스트 api",
            description = "서버 내 저장소에 파일을 업로드합니다."
    )
    public ApiResponse<?> fileUpload(
            @RequestParam("file") MultipartFile file,
            @RequestParam("name") String name
    ) throws IOException {
            FileDAO savedFile = fileService.uploadFile(file, name);
            return ApiResponse.onSuccess(Status.OK.getCode(), Status.OK.getMessage(), savedFile);
    }
}
