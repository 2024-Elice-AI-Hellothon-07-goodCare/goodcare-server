package com.goodcare.server.file.dao;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class FileDAO {

    @Id
    @Column(name="file_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="file_path")
    private String filePath;

    @Column(name="file_size")
    private Long fileSize;
}
