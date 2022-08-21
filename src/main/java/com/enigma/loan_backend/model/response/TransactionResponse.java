package com.enigma.loan_backend.model.response;

import com.enigma.loan_backend.entity.LoanTransaction;
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

    private String loanTypeId;

    private String instalmentTypeId;

    private String customerId;

    private Double nominal;

    private Long approvedAt;

    private String approvedBy;

    private ApprovalStatus approvalStatus;

    private List<TransactionDetailResponse> transactionDetailResponses;

    private Long createdAt;

    private Long updatedAt;

    public TransactionResponse(LoanTransaction loanTransaction) {
        this.id = loanTransaction.getId();
        this.loanTypeId = loanTransaction.getLoanType().getId();
        this.instalmentTypeId = loanTransaction.getInstalmentType().getId();
        this.customerId = loanTransaction.getCustomer().getId();
        this.nominal = loanTransaction.getNominal();
        this.approvedAt = loanTransaction.getApprovedAt();
        this.approvedBy = loanTransaction.getApprovedBy();
        this.approvalStatus = loanTransaction.getApprovalStatus();
        this.transactionDetailResponses = convertTransactionDetailToResponse(loanTransaction);
        this.createdAt = loanTransaction.getCreatedAt();
        this.updatedAt = loanTransaction.getUpdatedAt();
    }

    private List<TransactionDetailResponse> convertTransactionDetailToResponse(LoanTransaction loanTransaction) {
        return loanTransaction.getLoanTransactionDetails() != null ? loanTransaction.getLoanTransactionDetails().stream().map(TransactionDetailResponse::new).collect(Collectors.toList()) : null;
    }
}
