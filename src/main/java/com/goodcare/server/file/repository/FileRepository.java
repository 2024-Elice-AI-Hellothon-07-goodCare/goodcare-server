package com.goodcare.server.file.repository;

import com.goodcare.server.file.dao.FileDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<FileDAO, Long> {

}
