package com.enigma.loan_backend.model.response;

import com.enigma.loan_backend.entity.InstalmentType;
import com.enigma.loan_backend.entity.LoanTransaction;
import com.enigma.loan_backend.entity.LoanType;
import com.enigma.loan_backend.entity.my_enum.ApprovalStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponse {

    private String id;

    private LoanType loanType;

    private InstalmentTypeResponse instalmentType;

    private CustomerResponse customer;

    private Double nominal;

    private Long approvedAt;

    private String approvedBy;

    private String approvalStatus;

    private List<TransactionDetailResponse> transactionDetailResponses;

    private Long createdAt;

    private Long updatedAt;

    private Long rejectedAt;

    private String rejectedBy;

    public TransactionResponse(LoanTransaction loanTransaction) {
        this.id = loanTransaction.getId();
        this.loanType = loanTransaction.getLoanType();
        this.instalmentType = new InstalmentTypeResponse(loanTransaction.getInstalmentType().getId(), loanTransaction.getInstalmentType().getInstalmentType().getName());
        this.customer = new CustomerResponse(loanTransaction.getCustomer());
        this.nominal = loanTransaction.getNominal();
        this.approvedAt = loanTransaction.getApprovedAt();
        this.approvedBy = loanTransaction.getApprovedBy();
        this.rejectedBy = loanTransaction.getRejectedBy();
        this.rejectedAt = loanTransaction.getRejectedAt();
        this.approvalStatus = loanTransaction.getApprovalStatus().getStatus();
        this.transactionDetailResponses = convertTransactionDetailToResponse(loanTransaction);
        this.createdAt = loanTransaction.getCreatedAt();
        this.updatedAt = loanTransaction.getUpdatedAt();
    }

    private List<TransactionDetailResponse> convertTransactionDetailToResponse(LoanTransaction loanTransaction) {
        return loanTransaction.getLoanTransactionDetails() != null ? loanTransaction.getLoanTransactionDetails().stream().map(TransactionDetailResponse::new).collect(Collectors.toList()) : null;
    }
}
