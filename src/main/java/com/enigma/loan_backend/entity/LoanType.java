package com.enigma.loan_backend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "t_loan_type")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoanType {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "loan_type_id")
    private String id;

    private String type;

    @Column(name = "maximum_loan")
    private Double maxLoan;

}
