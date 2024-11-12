package com.goodcare.server.file.service;

import com.goodcare.server.file.dao.FileDAO;
import com.goodcare.server.file.repository.FileRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileService {

    @Autowired
    private final FileRepository fileRepository;
    
    // 음성파일 확장자 목록입니다.
    // 음성파일 지원 가능한 종류 확인 후 추후 추가 예정입니다.
    private static final List<String> ALLOW_EXTENSIONS = Arrays.asList("mp3", "wav", "ogg", "m4a");

    public FileDAO uploadFile(MultipartFile file, String name) throws IOException {
        String uploadDir = Paths.get(System.getProperty("user.dir"), "file", name).toString();
        
        // 파일 확장자 검사
        // 음성파일 아닌 경우 Exception 반환
        String filename = file.getOriginalFilename();
        if(filename != null){
            String fileExtension = filename.
                    substring(filename.lastIndexOf(".") + 1).toLowerCase();

            if(!ALLOW_EXTENSIONS.contains(fileExtension)){
                throw new IOException();
            }
        }

        // file/회원코드/음성파일명 의 경로로 생성 예정
        String filePath = Paths.get(uploadDir, file.getOriginalFilename()).toString();

        // 폴더가 없으면 생성
        File folder = new File(uploadDir);
        if (!folder.exists() && !folder.mkdirs()) {
            throw new IOException();
        }

        File destinationFile = new File(filePath);
        FileUtils.copyInputStreamToFile(file.getInputStream(), destinationFile);
        // 모든 사용자에게 읽기 권한 부여
        // 이거 없으면 파일 다운로드 안됨!!
        destinationFile.setReadable(true, false);

        // 추후 회원 정보 나온 후 DB 내 추가로 정보 저장 예정
        FileDAO fileDAO = new FileDAO();
        fileDAO.setFilePath(filePath);
        fileDAO.setFileSize(file.getSize());

        fileRepository.save(fileDAO);

        return fileDAO;
    }

    // 나중에 환자 코드 사용해서 파일 가져오는 것으로 바꾸기
    public ResponseEntity<Resource> downloadFile(Long id) throws IOException {
        FileDAO fileDAO = fileRepository.findById(id).orElseThrow(
                () -> new FileNotFoundException("This member has no file" + id));

        File file = new File(fileDAO.getFilePath());

        if(!file.exists()){
            throw new FileNotFoundException("파일을 찾을 수 없습니다.");
        }

        Resource resource = new FileSystemResource(file);
        String contentType = Files.probeContentType(file.toPath());
        long contentLength = file.length(); // 파일 크기 설정

        // Content-Type을 지정, 기본값은 audio/mpeg
        if (contentType == null || !contentType.startsWith("audio")) {
            contentType = "audio/mpeg";
        }

        // HTTP 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        // 바로 스트리밍 하기를 원하면 헤더 밑에줄로 바꾸기
//        headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getName() + "\"");
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"");
        headers.add(HttpHeaders.CONTENT_TYPE, contentType);

        // ResponseEntity로 반환
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(contentLength)
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource);
    }

}
