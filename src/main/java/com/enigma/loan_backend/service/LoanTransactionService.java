package com.enigma.loan_backend.service;

import com.enigma.loan_backend.entity.LoanTransaction;
import com.enigma.loan_backend.model.request.LoanTransactionApprovalRequest;
import com.enigma.loan_backend.model.response.TransactionDetailResponse;
import com.enigma.loan_backend.model.response.TransactionResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface LoanTransactionService {

    TransactionResponse create(LoanTransaction loanTransaction);

    TransactionResponse approvingLoanTransaction(LoanTransactionApprovalRequest request, String adminId);

    TransactionDetailResponse get(String id);

    Page<TransactionResponse> getAllPage(Pageable pageable);

    void payInstalment(String trxId, MultipartFile multipartFile);
}
