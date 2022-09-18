package com.enigma.loan_backend.service.impl;

import com.enigma.loan_backend.exception.NotAcceptableException;
import com.enigma.loan_backend.model.response.FileResponse;
import com.enigma.loan_backend.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

@Service
@Slf4j
public class FileServiceImpl implements FileService {

    @Value("${loan_backend.file.path}")
    private String root;
    private Path path;

    @Override
    public FileResponse create(MultipartFile multipartFile, String path) {
        try {
            this.path = Paths.get(root, path);
            Path file = Files.createDirectories(this.path);

            String filename = createFilename(multipartFile);
            Path saveFile = file.resolve(filename);
            Files.copy(multipartFile.getInputStream(), saveFile);

            log.info("created new file {} - at {}", filename, new Date());

            return new FileResponse(null, filename, this.path.toUri().getPath());
        } catch (IOException e) {
            throw new NotAcceptableException(e.getMessage());
        }
    }

    @Override
    public Resource get(String path, String filename) {
        this.path = Paths.get(path);
        Path resolve = this.path.resolve(filename);

        try {
            Resource resource = new UrlResource(resolve.toUri());
            if (!resource.isReadable()) throw new NotAcceptableException(".getMessage()Could not read the file!");

            log.info("get file {} - at {}", filename, new Date());

            return resource;
        } catch (MalformedURLException e) {
            throw new NotAcceptableException(e.getMessage());
        }
    }

    @Override
    public FileResponse update(String filename, String path, MultipartFile multipartFile) {
        this.path = Paths.get(path);
        Path oldFile = this.path.resolve(filename);

        String newFilename = createFilename(multipartFile);
        Path newFile = this.path.resolve(newFilename);
        try {
            Files.delete(oldFile);
            Files.copy(multipartFile.getInputStream(), newFile);
            return new FileResponse(null, newFilename, this.path.toUri().getPath());
        } catch (IOException e) {
            throw new NotAcceptableException(e.getMessage());
        }
    }

    @Override
    public void delete(String path, String filename) {
        this.path = Paths.get(path);
        Path file = this.path.resolve(filename);
        try {
            log.info("delete file {} - at {}", filename, new Date());
            Files.delete(file);
        } catch (IOException e) {
            throw new NotAcceptableException(e.getMessage());
        }
    }

    private String createFilename(MultipartFile multipartFile) {
        if (multipartFile.getOriginalFilename() == null)
            throw new ConstraintViolationException(String.format("content of %s this filename cannot be empty", multipartFile.getContentType()), null);
        return String.format("%d_%s", System.currentTimeMillis(), multipartFile.getOriginalFilename());
    }

}
