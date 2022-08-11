package com.enigma.loan_backend.service;

import com.enigma.loan_backend.model.response.FileResponse;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface LoanDocumentService {

    List<FileResponse> create(String customerId, List<MultipartFile> multipartFiles);

    List<FileResponse> getAll(String customerId);

    Resource get(String documentId);

}
