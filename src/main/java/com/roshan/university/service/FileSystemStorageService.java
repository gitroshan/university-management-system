package com.roshan.university.service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileSystemStorageService {

    void store(MultipartFile file);

    Resource loadAsResource(String filename);

    List<Path> listSourceFiles(Path dir) throws IOException;
}
