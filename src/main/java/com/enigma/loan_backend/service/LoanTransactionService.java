package com.enigma.loan_backend.service;

import com.enigma.loan_backend.entity.LoanTransaction;
import com.enigma.loan_backend.model.request.LoanTransactionApprovalRequest;
import com.enigma.loan_backend.model.response.TransactionDetailResponse;
import com.enigma.loan_backend.model.response.TransactionResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

public interface LoanTransactionService {

    TransactionResponse create(LoanTransaction loanTransaction);

    TransactionResponse get(String id);

    TransactionResponse approvingLoanTransaction(LoanTransactionApprovalRequest request, Authentication authentication);

    TransactionDetailResponse getTransactionDetail(String id);

    Page<TransactionResponse> getAllByCustomerId(String customerId, Pageable pageable);

    Page<TransactionResponse> getAllPage(Pageable pageable);

    void payInstalment(String trxId, MultipartFile multipartFile);

    TransactionResponse rejectingTransaction(String trxId, Authentication authentication);
}
