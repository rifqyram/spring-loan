package com.enigma.loan_backend.service.impl;

import com.enigma.loan_backend.entity.LoanTransaction;
import com.enigma.loan_backend.repository.LoanTransactionRepository;
import com.enigma.loan_backend.service.LoanTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoanTransactionServiceImpl implements LoanTransactionService {

    LoanTransactionRepository loanTransactionRepository;

    @Autowired
    public LoanTransactionServiceImpl(LoanTransactionRepository loanTransactionRepository) {
        this.loanTransactionRepository = loanTransactionRepository;
    }

    @Override
    public LoanTransaction createTransaction(LoanTransaction loanTransaction) {
        return loanTransactionRepository.save(loanTransaction);
    }
}
