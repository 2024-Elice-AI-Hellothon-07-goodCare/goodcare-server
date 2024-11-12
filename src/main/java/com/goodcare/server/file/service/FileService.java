package com.goodcare.server.file.service;

import com.goodcare.server.file.dao.FileDAO;
import com.goodcare.server.file.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
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
    
        // 추후 회원 정보 나온 후 DB 내 추가로 정보 저장 예정
        FileDAO fileDAO = new FileDAO();
        fileDAO.setFilePath(filePath);
        fileDAO.setFileSize(file.getSize());

        fileRepository.save(fileDAO);

        return fileDAO;
    }
}
