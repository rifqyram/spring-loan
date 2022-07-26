package com.enigma.loan_backend.service;

import com.enigma.loan_backend.entity.LoanType;

import java.util.List;

public interface LoanTypeService {
    LoanType saveLoanType(LoanType loanType);
    LoanType getLoanTypeById(String id);
    List<LoanType> getAllLoanType();
    void deleteLoanType(String id);
}
