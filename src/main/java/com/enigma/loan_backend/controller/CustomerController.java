package com.enigma.loan_backend.controller;

import com.enigma.loan_backend.entity.Customer;
import com.enigma.loan_backend.model.response.CommonResponse;
import com.enigma.loan_backend.model.response.FileResponse;
import com.enigma.loan_backend.service.CustomerService;
import com.enigma.loan_backend.service.LoanDocumentService;
import com.enigma.loan_backend.service.ProfilePictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class CustomerController {
    private final CustomerService customerService;
    private final ProfilePictureService profilePictureService;
    private final LoanDocumentService loanDocumentService;

    @Autowired
    public CustomerController(CustomerService customerService, ProfilePictureService profilePictureService, LoanDocumentService loanDocumentService) {
        this.customerService = customerService;
        this.profilePictureService = profilePictureService;
        this.loanDocumentService = loanDocumentService;
    }

    @PostMapping("/customers")
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerService.saveCustomer(customer);
    }

    @GetMapping("/customers/{id}")
    public Customer getCustomerById(@PathVariable String id) {
        return customerService.getCustomerById(id);
    }

    @GetMapping("/customers")
    public List<Customer> getAllCustomer() {
        return customerService.getCustomerList();
    }

    @PutMapping("/customers")
    public Customer updateCustomer(@RequestBody Customer customer) {
        return customerService.saveCustomer(customer);
    }

    @DeleteMapping("/customers/{id}")
    public void deleteCustomer(@PathVariable String id) {
        customerService.deleteCustomer(id);
    }

    @PostMapping("/customers/{id}/upload/avatar")
    public ResponseEntity<?> uploadAvatar(@PathVariable String id, @RequestPart MultipartFile avatar) {
        FileResponse profilePicture = profilePictureService.create(id, avatar);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CommonResponse<>(
                HttpStatus.CREATED.value(),
                HttpStatus.CREATED.name(),
                "Successfully upload avatar",
                profilePicture
        ));
    }

    @GetMapping("/customers/{customerId}/avatar")
    public ResponseEntity<?> getAvatar(@PathVariable(name = "customerId") String id) {
        Resource resource = profilePictureService.get(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @DeleteMapping("/customers/{customerId}/avatar")
    public ResponseEntity<?> deleteAvatar(@PathVariable(name = "customerId") String id) {
        profilePictureService.delete(id);
        return ResponseEntity
                .ok(new CommonResponse<>(
                        HttpStatus.OK.value(),
                        HttpStatus.OK.name(),
                        "Successfully delete avatar",
                        null
                ));
    }

    @PostMapping("/customers/{customerId}/documents")
    public ResponseEntity<?> uploadDocument(@PathVariable String customerId, @RequestPart List<MultipartFile> documents) {
        List<FileResponse> loanDocuments = loanDocumentService.create(customerId, documents);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new CommonResponse<>(
                        HttpStatus.CREATED.value(),
                        HttpStatus.CREATED.name(),
                        "Successfully upload documents",
                        loanDocuments
                ));
    }

    @GetMapping("/customers/{customerId}/myDocuments")
    public ResponseEntity<?> getDocuments(@PathVariable String customerId) {
        List<FileResponse> loanDocumentResponses = loanDocumentService.getAll(customerId);
        return ResponseEntity
                .ok(new CommonResponse<>(
                        HttpStatus.OK.value(),
                        HttpStatus.OK.name(),
                        "Successfully fetch documents",
                        loanDocumentResponses
                ));
    }

    @GetMapping("/customers/{documentId}/documents")
    public ResponseEntity<?> readDocument(@PathVariable String documentId) {
        Resource resource = loanDocumentService.get(documentId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
