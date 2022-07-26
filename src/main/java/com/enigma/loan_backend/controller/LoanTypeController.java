package com.enigma.loan_backend.controller;

import com.enigma.loan_backend.entity.LoanType;
import com.enigma.loan_backend.service.LoanTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LoanTypeController {
    LoanTypeService loanTypeService;

    @Autowired
    public LoanTypeController(LoanTypeService loanTypeService) {
        this.loanTypeService = loanTypeService;
    }

    @PostMapping("/loan-types")
    public LoanType createLoanType(@RequestBody LoanType loanType){
        return loanTypeService.saveLoanType(loanType);
    }

    @GetMapping("/loan-types/{id}")
    public LoanType getLoanTypeById(@PathVariable String id){
        return loanTypeService.getLoanTypeById(id);
    }

    @GetMapping("/loan-types")
    public List<LoanType> getAllLoanType(){
        return  loanTypeService.getAllLoanType();
    }

    @PutMapping("/loan-types")
    public LoanType updateCustomer(@RequestBody LoanType loanType){
        return loanTypeService.saveLoanType(loanType);
    }

    @DeleteMapping("/loan-types/id")
    public void deleteCustomer(@PathVariable String id){
        loanTypeService.deleteLoanType(id);
    }
}
