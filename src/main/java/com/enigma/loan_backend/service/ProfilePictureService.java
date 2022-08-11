package com.enigma.loan_backend.service;

import com.enigma.loan_backend.entity.ProfilePicture;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface ProfilePictureService {

    ProfilePicture create(String id, MultipartFile multipartFile);
    Resource get(String id);
    ProfilePicture update(String id, MultipartFile multipartFile);
    void delete(String id);

}
