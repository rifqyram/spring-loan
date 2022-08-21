package com.enigma.loan_backend.model.response;

import com.enigma.loan_backend.constant.Constant;
import com.enigma.loan_backend.entity.LoanTransactionDetail;
import com.enigma.loan_backend.entity.my_enum.LoanStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDetailResponse {

    private String id;

    private Long transactionDate;

    private Double nominal;

    private String guaranteePicture;

    private LoanStatus loanStatus;

    private Long createdAt;

    private Long updatedAt;

    public TransactionDetailResponse(LoanTransactionDetail loanTransactionDetail) {
        this.id = loanTransactionDetail.getId();
        this.transactionDate = loanTransactionDetail.getTransactionDate();
        this.nominal = loanTransactionDetail.getNominal();
        this.guaranteePicture = getGuaranteePicture(loanTransactionDetail);
        this.loanStatus = loanTransactionDetail.getLoanStatus();
        this.createdAt = loanTransactionDetail.getCreatedAt();
        this.updatedAt = loanTransactionDetail.getUpdatedAt();
    }

    public String getGuaranteePicture(LoanTransactionDetail loanTransactionDetail) {
        return loanTransactionDetail.getGuaranteePicture() != null ? String.format(Constant.API_GET_GUARANTEE_PIC, loanTransactionDetail.getGuaranteePicture().getId()) : null;
    }
}
