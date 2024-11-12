package com.goodcare.server.file.service;

import com.goodcare.server.file.dao.FileDAO;
import com.goodcare.server.file.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class FileService {

    @Autowired
    private final FileRepository fileRepository;

    public FileDAO uploadFile(MultipartFile file, String name) throws IOException {
        String uploadDir = Paths.get(System.getProperty("user.dir"), "file", name).toString();

        String filePath = Paths.get(uploadDir, file.getOriginalFilename()).toString();

        // 폴더가 없으면 생성
        File folder = new File(uploadDir);
        if (!folder.exists()) {
            boolean mkdirs = folder.mkdirs();
            if (!mkdirs) {
                throw new IOException("Failed to create directory: " + uploadDir);
            }
        }

        File destinationFile = new File(filePath);
        FileUtils.copyInputStreamToFile(file.getInputStream(), destinationFile);

        FileDAO fileDAO = new FileDAO();
        fileDAO.setFilePath(filePath);
        fileDAO.setFileSize(file.getSize());

        fileRepository.save(fileDAO);

        return fileDAO;
    }
}
