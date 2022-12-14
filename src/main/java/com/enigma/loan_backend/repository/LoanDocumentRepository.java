package com.enigma.loan_backend.repository;

import com.enigma.loan_backend.entity.LoanDocument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanDocumentRepository extends JpaRepository<LoanDocument, String> {
    List<LoanDocument> findAllByCustomerId(String customerId);
}
