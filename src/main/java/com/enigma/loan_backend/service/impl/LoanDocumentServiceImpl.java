package com.enigma.loan_backend.service.impl;

import com.enigma.loan_backend.constant.Constant;
import com.enigma.loan_backend.entity.Customer;
import com.enigma.loan_backend.entity.LoanDocument;
import com.enigma.loan_backend.exception.NotFoundException;
import com.enigma.loan_backend.model.response.FileResponse;
import com.enigma.loan_backend.repository.LoanDocumentRepository;
import com.enigma.loan_backend.service.CustomerService;
import com.enigma.loan_backend.service.FileService;
import com.enigma.loan_backend.service.LoanDocumentService;
import com.enigma.loan_backend.utils.Utility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class LoanDocumentServiceImpl implements LoanDocumentService {

    private final LoanDocumentRepository loanDocumentRepository;
    private final CustomerService customerService;
    private final FileService fileService;

    @Override
    public List<FileResponse> create(String customerId, List<MultipartFile> multipartFiles) {
        Customer customer = customerService.get(customerId);

        List<FileResponse> fileResponses = new ArrayList<>();

        multipartFiles.forEach(multipartFile -> {
            boolean validationContentType = Utility.validateContentTypeDocument(multipartFile.getContentType());
            if (!validationContentType) throw new ConstraintViolationException(String.format("unsupported of content type %s", multipartFile.getContentType()), null);

            FileResponse fileResponse = fileService.create(multipartFile, Constant.PATH_FILES_DOCUMENT);

            LoanDocument loanDocument = new LoanDocument(fileResponse.getName(), multipartFile.getContentType(), fileResponse.getUrl(), multipartFile.getSize(), customer);
            LoanDocument savedDocument = loanDocumentRepository.save(loanDocument);

            String url = String.format(Constant.API_GET_DOCUMENT, savedDocument.getId());
            fileResponses.add(new FileResponse(fileResponse.getName(), url));
        });

        return fileResponses;
    }

    @Override
    public List<FileResponse> getAll(String customerId) {
        List<LoanDocument> loanDocuments = loanDocumentRepository.findAll();
        return loanDocuments.stream().map(loanDocument -> {
            String url = String.format(Constant.API_GET_DOCUMENT, loanDocument.getId());
            return new FileResponse(loanDocument.getName(), url);
        }).collect(Collectors.toList());
    }

    @Override
    public Resource get(String documentId) {
        LoanDocument loanDocument = findByIdOrThrowNotFound(documentId);
        return fileService.get(loanDocument.getPath(), loanDocument.getName());
    }

    private LoanDocument findByIdOrThrowNotFound(String documentId) {
        return loanDocumentRepository.findById(documentId).orElseThrow(() -> new NotFoundException(String.format("document not found with id %s", documentId)));
    }
}
