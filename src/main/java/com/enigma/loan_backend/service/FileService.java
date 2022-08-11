package com.enigma.loan_backend.service;

import com.enigma.loan_backend.model.response.FileResponse;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    FileResponse create(MultipartFile multipartFile, String path);

    Resource get(String path, String filename);

    FileResponse update(String filename, String path, MultipartFile multipartFile);

    void delete(String path, String filename);

}
