package com.enigma.loan_backend.service;

import com.enigma.loan_backend.entity.LoanTransactionDetail;
import com.enigma.loan_backend.model.response.TransactionDetailResponse;
import org.springframework.web.multipart.MultipartFile;

public interface LoanTransactionDetailService {
    LoanTransactionDetail create(LoanTransactionDetail loanTransactionDetail);
    TransactionDetailResponse get(String id);

    void payInstalment(LoanTransactionDetail loanTransactionDetail, MultipartFile multipartFile);

}
