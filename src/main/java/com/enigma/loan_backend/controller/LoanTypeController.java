package com.enigma.loan_backend.controller;

import com.enigma.loan_backend.entity.LoanType;
import com.enigma.loan_backend.model.response.CommonResponse;
import com.enigma.loan_backend.service.LoanTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Loan Type")
public class LoanTypeController {
    LoanTypeService loanTypeService;

    @Autowired
    public LoanTypeController(LoanTypeService loanTypeService) {
        this.loanTypeService = loanTypeService;
    }

    @Operation(summary = "Create Loan Type")
    @SecurityRequirement(name = "Authorization")
    @PostMapping("/loan-types")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<?> createLoanType(@RequestBody LoanType loanType){
        LoanType type = loanTypeService.saveLoanType(loanType);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CommonResponse<>(
                        HttpStatus.CREATED.value(),
                        HttpStatus.CREATED.name(),
                        "Successfuly create loan type",
                        type
                ));
    }

    @Operation(summary = "Get Loan Type")
    @SecurityRequirement(name = "Authorization")
    @GetMapping("/loan-types/{id}")
    public ResponseEntity<?> getLoanTypeById(@PathVariable String id){
        LoanType loanType = loanTypeService.getLoanTypeById(id);
        return ResponseEntity.ok(new CommonResponse<>(
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                "Successfully fetch loan type",
                loanType
        ));
    }

    @Operation(summary = "Get All Loan Type")
    @SecurityRequirement(name = "Authorization")
    @GetMapping("/loan-types")
    public ResponseEntity<?> getAllLoanType(){
        List<LoanType> loanType = loanTypeService.getAllLoanType();
        return ResponseEntity.ok(new CommonResponse<>(
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                "Successfully fetch loan type",
                loanType
        ));
    }

    @Operation(summary = "Update Loan Type")
    @SecurityRequirement(name = "Authorization")
    @PutMapping("/loan-types")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<?> updateCustomer(@RequestBody LoanType loanType){
        LoanType type = loanTypeService.saveLoanType(loanType);
        return ResponseEntity.ok(new CommonResponse<>(
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                "Successfully update loan type",
                type
        ));
    }

    @Operation(summary = "Delete Loan Type")
    @SecurityRequirement(name = "Authorization")
    @DeleteMapping("/loan-types/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<?> deleteCustomer(@PathVariable String id){
        loanTypeService.deleteLoanType(id);
        return ResponseEntity.ok(new CommonResponse<>(
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                "Successfully delete loan type",
                null
        ));
    }
}
