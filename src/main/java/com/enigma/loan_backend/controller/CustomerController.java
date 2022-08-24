package com.enigma.loan_backend.controller;

import com.enigma.loan_backend.entity.Customer;
import com.enigma.loan_backend.model.response.CommonResponse;
import com.enigma.loan_backend.model.response.CustomerResponse;
import com.enigma.loan_backend.model.response.FileResponse;
import com.enigma.loan_backend.service.CustomerService;
import com.enigma.loan_backend.service.LoanDocumentService;
import com.enigma.loan_backend.service.ProfilePictureService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@Tag(name = "Customer")
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

    @Operation(summary = "Get Customer")
    @SecurityRequirement(name = "Authorization")
    @GetMapping("/customers/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable String id) {
        CustomerResponse customer = customerService.getCustomerById(id);
        return ResponseEntity.ok(new CommonResponse<>(
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                "Successfully fetch user",
                customer
        ));
    }

    @Operation(summary = "Get Self Customer")
    @SecurityRequirement(name = "Authorization")
    @GetMapping("/customers/me")
    public ResponseEntity<?> getSelfCustomer(Authentication authentication) {
        CustomerResponse customerResponse = customerService.getByToken(authentication);
        return ResponseEntity.ok(new CommonResponse<>(
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                "Successfully fetch user",
                customerResponse
        ));
    }

    @Operation(summary = "Get All Customer")
    @SecurityRequirement(name = "Authorization")
    @GetMapping("/customers")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<?> getAllCustomer() {
        List<CustomerResponse> customers = customerService.getCustomerList();
        return ResponseEntity.ok(new CommonResponse<>(
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                "Successfully fetch user",
                customers
        ));
    }

    @Operation(summary = "Update Customer")
    @SecurityRequirement(name = "Authorization")
    @PutMapping("/customers")
    @PreAuthorize("hasAnyRole('CUSOMER')")
    public ResponseEntity<?> updateCustomer(@RequestBody Customer customer) {
        Customer saveCustomer = customerService.saveCustomer(customer);
        return ResponseEntity.ok(new CommonResponse<>(
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                "Successfully update user",
                saveCustomer
        ));
    }

    @Operation(summary = "Delete Customer")
    @SecurityRequirement(name = "Authorization")
    @DeleteMapping("/customers/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable String id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.ok(new CommonResponse<>(
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                "Successfully delete user",
                null
        ));
    }

    @Operation(summary = "Upload Avatar")
    @SecurityRequirement(name = "Authorization")
    @PostMapping(value = "/customers/{customerId}/upload/avatar" , consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> uploadAvatar(@PathVariable String customerId,
                                          @Parameter(
                                                  description = "Files to be uploaded",
                                                  content = @Content(mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE)
                                          )
                                          @RequestPart MultipartFile avatar) {
        FileResponse profilePicture = profilePictureService.create(customerId, avatar);
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(new CommonResponse<>(
                        HttpStatus.CREATED.value(),
                        HttpStatus.CREATED.name(),
                        "Successfully upload avatar",
                        profilePicture
                ));
    }

    @Operation(summary = "Download Avatar")
    @SecurityRequirement(name = "Authorization")
    @GetMapping("/customers/{customerId}/avatar")
    public ResponseEntity<?> getAvatar(@PathVariable(name = "customerId") String id) {
        Resource resource = profilePictureService.get(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @Operation(summary = "Delete Avatar")
    @SecurityRequirement(name = "Authorization")
    @DeleteMapping("/customers/{customerId}/avatar")
    @PreAuthorize("hasAnyRole('CUSTOMER')")
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

    @Operation(summary = "Upload Loan Document")
    @SecurityRequirement(name = "Authorization")
    @PostMapping(value = "/customers/{customerId}/documents", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> uploadDocument(@PathVariable String customerId,
                                            @Parameter(
                                                    description = "Files to be uploaded",
                                                    content = @Content(mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE)  // Won't work without OCTET_STREAM as the mediaType.
                                            )
                                            @RequestPart List<MultipartFile> documents) {
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

    @Operation(summary = "Get All Loan Document")
    @SecurityRequirement(name = "Authorization")
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

    @Operation(summary = "Download Loan Document")
    @SecurityRequirement(name = "Authorization")
    @GetMapping("/customers/{documentId}/documents")
    public ResponseEntity<?> readDocument(@PathVariable String documentId) {
        Resource resource = loanDocumentService.get(documentId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
