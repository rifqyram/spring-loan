package com.enigma.loan_backend.repository;

import com.enigma.loan_backend.entity.LoanTransactionDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoanTransactionDetailRepository extends JpaRepository<LoanTransactionDetail, String> {
}
