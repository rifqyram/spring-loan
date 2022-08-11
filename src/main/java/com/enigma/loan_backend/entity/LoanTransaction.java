package com.enigma.loan_backend.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "trx_loan")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class LoanTransaction {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "loan_transaction_id")
    private String id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date transactionDate;

    private boolean approvalStatus;

    private boolean loanStatus;

    private Double nominal;

    private String guaranteePicture;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    @JsonIgnoreProperties("loanTransactions")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loan_type_id", referencedColumnName = "loan_type_id")
    @JsonIgnoreProperties("loanTransactions")
    private LoanType loanType;
}
