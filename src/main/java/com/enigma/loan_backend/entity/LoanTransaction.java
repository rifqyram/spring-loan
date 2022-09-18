package com.enigma.loan_backend.entity;

import com.enigma.loan_backend.entity.my_enum.ApprovalStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "trx_loan")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoanTransaction {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String id;

    @ManyToOne
    @JoinColumn(name = "loan_type_id")
    private LoanType loanType;

    @ManyToOne
    @JoinColumn(name = "instalment_type_id")
    private InstalmentType instalmentType;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private Double nominal;

    private Long approvedAt;

    private String approvedBy;

    private Long rejectedAt;

    private String rejectedBy;

    @Enumerated(EnumType.STRING)
    private ApprovalStatus approvalStatus;

    @OneToMany(mappedBy = "loanTransaction")
    @JsonIgnoreProperties("loanTransaction")
    private List<LoanTransactionDetail> loanTransactionDetails;

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
