package com.enigma.loan_backend.controller;

import com.enigma.loan_backend.entity.LoanTransaction;
import com.enigma.loan_backend.model.request.LoanTransactionApprovalRequest;
import com.enigma.loan_backend.model.response.CommonResponse;
import com.enigma.loan_backend.model.response.PageResponse;
import com.enigma.loan_backend.model.response.TransactionResponse;
import com.enigma.loan_backend.service.GuaranteePictureService;
import com.enigma.loan_backend.service.LoanTransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final LoanTransactionService loanTransactionService;
    private final GuaranteePictureService guaranteePictureService;

    @Operation(summary = "Request Loan")
    @SecurityRequirement(name = "Authorization")
    @PreAuthorize("hasAnyRole('CUSTOMER')")
    @PostMapping
    public ResponseEntity<?> createRequestLoanTransaction(@RequestBody LoanTransaction loanTransaction) {
        TransactionResponse transaction = loanTransactionService.create(loanTransaction);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new CommonResponse<>(
                        HttpStatus.CREATED.value(),
                        HttpStatus.CREATED.name(),
                        "Successfully request transaction",
                        transaction
                ));
    }

    @Operation(summary = "Get All Transaction")
    @SecurityRequirement(name = "Authorization")
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "0") Integer page,
                                    @RequestParam(defaultValue = "5") Integer size,
                                    @RequestParam(defaultValue = "DESC") String direction,
                                    @RequestParam(defaultValue = "createdAt") String sortBy) {
        Sort sort = Sort.by(Sort.Direction.valueOf(direction), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<TransactionResponse> transactions = loanTransactionService.getAllPage(pageable);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponse<>(
                        HttpStatus.OK.value(),
                        HttpStatus.OK.name(),
                        "Successfully fetch transaction data",
                        new PageResponse<>(transactions)
                ));
    }

    @Operation(summary = "Get Transaction By Id")
    @SecurityRequirement(name = "Authorization")
    @GetMapping("/details/{detailId}")
    public ResponseEntity<?> getTransactionDetailById(@PathVariable String detailId) {
        TransactionResponse transactionResponse = loanTransactionService.get(detailId);
        return ResponseEntity.ok(
                new CommonResponse<>(
                        HttpStatus.OK.value(),
                        HttpStatus.OK.name(),
                        "Successfully approved transaction",
                        transactionResponse
                ));
    }

    @Operation(summary = "Approve Transaction By Admin")
    @SecurityRequirement(name = "Authorization")
    @PutMapping("/{adminId}/approve")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<?> approvingLoanTransaction(@RequestBody LoanTransactionApprovalRequest request, @PathVariable String adminId) {
        TransactionResponse transaction = loanTransactionService.approvingLoanTransaction(request, adminId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new CommonResponse<>(
                        HttpStatus.OK.value(),
                        HttpStatus.OK.name(),
                        "Successfully approved transaction",
                        transaction
                ));
    }

    @Operation(summary = "Pay Instalment")
    @SecurityRequirement(name = "Authorization")
    @PutMapping("/{trxId}/pay")
    @PreAuthorize("hasAnyRole('CUSTOMER')")
    public ResponseEntity<?> payInstalment(@PathVariable String trxId, @RequestPart MultipartFile guaranteePicture) {
        loanTransactionService.payInstalment(trxId, guaranteePicture);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponse<>(
                        HttpStatus.OK.value(),
                        HttpStatus.OK.name(),
                        "Successfully pay instalment",
                        null
                ));
    }

    @Operation(summary = "Download Guarantee Picture")
    @SecurityRequirement(name = "Authorization")
    @GetMapping("/{fileId}/file")
    public ResponseEntity<?> downloadGuaranteePicture(@PathVariable String fileId) {
        Resource resource = guaranteePictureService.get(fileId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

}
