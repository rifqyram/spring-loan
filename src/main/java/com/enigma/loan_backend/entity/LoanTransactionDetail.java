package com.enigma.loan_backend.entity;


import com.enigma.loan_backend.entity.my_enum.LoanStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "trx_loan_detail")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoanTransactionDetail {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String id;

    private Long transactionDate;

    private Double nominal;

    @ManyToOne
    @JoinColumn(name = "trx_loan_id")
    private LoanTransaction loanTransaction;

    @OneToOne
    private GuaranteePicture guaranteePicture;

    @Enumerated(EnumType.STRING)
    private LoanStatus loanStatus;

    private Long createdAt;

    private Long updatedAt;

    @PrePersist
    private void onPersist() {
        if (createdAt == null) createdAt = System.currentTimeMillis();
    }

    @PreUpdate
    private void onUpdate() {
        if (updatedAt == null) updatedAt = System.currentTimeMillis();
    }

}
