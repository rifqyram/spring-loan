package com.enigma.loan_backend.service;

import com.enigma.loan_backend.entity.LoanTransaction;

public interface LoanTransactionService {
    LoanTransaction createTransaction(LoanTransaction loanTransaction);
}
