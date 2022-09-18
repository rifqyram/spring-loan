package com.enigma.loan_backend.repository;

import com.enigma.loan_backend.entity.LoanTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LoanTransactionRepository extends JpaRepository<LoanTransaction, String> {
    Page<LoanTransaction> findAllByCustomerId(String customerId, Pageable pageable);
}
