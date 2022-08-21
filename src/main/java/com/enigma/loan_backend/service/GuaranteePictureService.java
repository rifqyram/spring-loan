package com.enigma.loan_backend.service;

import com.enigma.loan_backend.entity.GuaranteePicture;
import com.enigma.loan_backend.model.response.FileResponse;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface GuaranteePictureService {

    FileResponse create(MultipartFile multipartFile);

    GuaranteePicture save(MultipartFile multipartFile);

    Resource get(String id);

}
