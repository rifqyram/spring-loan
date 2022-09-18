package com.enigma.loan_backend.service.impl;

import com.enigma.loan_backend.constant.Constant;
import com.enigma.loan_backend.entity.GuaranteePicture;
import com.enigma.loan_backend.exception.NotFoundException;
import com.enigma.loan_backend.model.response.FileResponse;
import com.enigma.loan_backend.repository.GuaranteePictureRepository;
import com.enigma.loan_backend.service.FileService;
import com.enigma.loan_backend.service.GuaranteePictureService;
import com.enigma.loan_backend.utils.Utility;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintViolationException;

@Service
@RequiredArgsConstructor
public class GuaranteePictureServiceImpl implements GuaranteePictureService {

    private final FileService fileService;
    private final GuaranteePictureRepository guaranteePictureRepository;

    @Override
    public FileResponse create(MultipartFile multipartFile) {
        FileResponse fileResponse = fileService.create(multipartFile, Constant.PATH_FILES_IMAGE);

        boolean validateImage = Utility.validateContentTypeImage(multipartFile.getContentType());
        if (!validateImage) throw new ConstraintViolationException(String.format("unsupported of content type %s", multipartFile.getContentType()), null);

        GuaranteePicture guaranteePicture = new GuaranteePicture(
                null,
                fileResponse.getName(),
                multipartFile.getContentType(),
                fileResponse.getUrl(),
                multipartFile.getSize()
        );

        GuaranteePicture savedGuaranteePicture = guaranteePictureRepository.save(guaranteePicture);
        String url = String.format(Constant.API_GET_GUARANTEE_PIC, savedGuaranteePicture.getId());
        return new FileResponse(savedGuaranteePicture.getId(), multipartFile.getName(), url);
    }

    @Override
    public GuaranteePicture save(MultipartFile multipartFile) {
        FileResponse fileResponse = fileService.create(multipartFile, Constant.PATH_FILES_IMAGE);

        GuaranteePicture guaranteePicture = new GuaranteePicture(
                null,
                fileResponse.getName(),
                multipartFile.getContentType(),
                fileResponse.getUrl(),
                multipartFile.getSize()
        );

        return guaranteePictureRepository.save(guaranteePicture);
    }

    @Override
    public Resource get(String id) {
        GuaranteePicture guaranteePicture = findByIdOrThrowNotFound(id);
        return fileService.get(guaranteePicture.getPath(), guaranteePicture.getName());
    }

    public GuaranteePicture findByIdOrThrowNotFound(String id) {
        return guaranteePictureRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("guarantee picture not found with id %s", id)));
    }
}
