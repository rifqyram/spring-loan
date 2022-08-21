package com.enigma.loan_backend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "t_loan_document")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoanDocument {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String id;

    private String name;

    private String contentType;

    private String path;

    private long size;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Customer.class)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public LoanDocument(String name, String contentType, String path, long size, Customer customer) {
        this.name = name;
        this.contentType = contentType;
        this.path = path;
        this.size = size;
        this.customer = customer;
    }
}
