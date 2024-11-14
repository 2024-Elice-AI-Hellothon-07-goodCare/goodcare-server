package com.goodcare.server.domain.uploadedFile.repository;

import com.goodcare.server.domain.uploadedFile.dao.FileDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<FileDAO, Long> {

}
