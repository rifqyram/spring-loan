package com.enigma.loan_backend.service.impl;

import com.enigma.loan_backend.entity.LoanType;
import com.enigma.loan_backend.repository.LoanTypeRepository;
import com.enigma.loan_backend.service.LoanTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanTypeServiceImpl implements LoanTypeService {

    LoanTypeRepository loanTypeRepository;

    @Autowired
    public LoanTypeServiceImpl(LoanTypeRepository loanTypeRepository) {
        this.loanTypeRepository = loanTypeRepository;
    }

    @Override
    public LoanType saveLoanType(LoanType loanType) {
        return loanTypeRepository.save(loanType);
    }

    @Override
    public LoanType getLoanTypeById(String id) {
         return loanTypeRepository.findById(id).orElseGet(() -> null);
    }

    @Override
    public List<LoanType> getAllLoanType() {
        return loanTypeRepository.findAll();
    }

    @Override
    public void deleteLoanType(String id) {
        loanTypeRepository.deleteById(id);
    }
}
