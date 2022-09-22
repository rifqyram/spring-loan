package com.enigma.loan_backend.service;

import com.enigma.loan_backend.entity.LoanDocument;
import com.enigma.loan_backend.model.response.FileResponse;
import org.springframework.core.io.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface LoanDocumentService {

    List<FileResponse> create(String customerId, List<MultipartFile> multipartFiles);

    List<FileResponse> getAll();

    Resource get(String documentId);

    List<LoanDocument> getByCustomerToken(Authentication authentication);

}
