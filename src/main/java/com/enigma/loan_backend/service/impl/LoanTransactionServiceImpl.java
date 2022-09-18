package com.enigma.loan_backend.service.impl;

import com.enigma.loan_backend.entity.*;
import com.enigma.loan_backend.entity.my_enum.EInstalmentType;
import com.enigma.loan_backend.exception.NotAcceptableException;
import com.enigma.loan_backend.exception.NotFoundException;
import com.enigma.loan_backend.model.request.LoanTransactionApprovalRequest;
import com.enigma.loan_backend.model.response.TransactionDetailResponse;
import com.enigma.loan_backend.model.response.TransactionResponse;
import com.enigma.loan_backend.model.response.UserResponse;
import com.enigma.loan_backend.repository.LoanTransactionRepository;
import com.enigma.loan_backend.service.*;
import com.enigma.loan_backend.utils.LoanCalculation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

import static com.enigma.loan_backend.entity.my_enum.ApprovalStatus.*;
import static com.enigma.loan_backend.entity.my_enum.LoanStatus.UNPAID;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoanTransactionServiceImpl implements LoanTransactionService {

    private final LoanTransactionRepository loanTransactionRepository;
    private final LoanTransactionDetailService loanTransactionDetailService;
    private final LoanTypeService loanTypeService;
    private final InstalmentTypeService instalmentTypeService;
    private final UserService userService;
    private final CustomerService customerService;

    @Override
    public TransactionResponse create(LoanTransaction loanTransaction) {
        LoanType loanType = loanTypeService.getLoanTypeById(loanTransaction.getLoanType().getId());
        Customer customer = customerService.get(loanTransaction.getCustomer().getId());
        InstalmentType instalmentType = instalmentTypeService.findById(loanTransaction.getInstalmentType().getId());

        loanTransaction.setCustomer(customer);
        loanTransaction.setInstalmentType(instalmentType);
        loanTransaction.setLoanType(loanType);

        if (loanTransaction.getNominal() > loanType.getMaxLoan()) throw new NotAcceptableException("nominal exceed");

        loanTransaction.setApprovalStatus(ON_PROGRESS);
        return new TransactionResponse(loanTransactionRepository.save(loanTransaction));
    }

    @Override
    public TransactionResponse get(String id) {
        LoanTransaction loanTransaction = findByIdOrThrowNotFound(id);
        return new TransactionResponse(loanTransaction);
    }

    @Override
    @Transactional
    public TransactionResponse approvingLoanTransaction(LoanTransactionApprovalRequest request, Authentication authentication) {
        UserResponse userResponse = userService.getByToken(authentication);
        LoanTransaction transaction = findByIdOrThrowNotFound(request.getLoanTransactionId());

        if (transaction.getApprovalStatus().equals(APPROVED) || transaction.getApprovalStatus().equals(REJECTED)) throw new NotAcceptableException("Transaction already proceed");

        EInstalmentType instalmentType = transaction.getInstalmentType().getInstalmentType();
        LoanCalculation loanCalculation = new LoanCalculation(transaction.getNominal(), request.getInterestRates(), instalmentType.getNumber());

        for (int i = 0; i < instalmentType.getNumber(); i++) {
            createLoanTransactionDetail(transaction, loanCalculation);
        }

        transaction.setApprovalStatus(APPROVED);
        transaction.setApprovedBy(userResponse.getEmail());
        transaction.setApprovedAt(System.currentTimeMillis());

        return new TransactionResponse(transaction);
    }

    @Override
    public TransactionDetailResponse getTransactionDetail(String id) {
        return loanTransactionDetailService.get(id);
    }

    @Override
    public Page<TransactionResponse> getAllByCustomerId(String customerId, Pageable pageable) {
        return loanTransactionRepository.findAllByCustomerId(customerId, pageable).map(TransactionResponse::new);
    }

    @Override
    public Page<TransactionResponse> getAllPage(Pageable pageable) {
        return loanTransactionRepository.findAll(pageable).map(TransactionResponse::new);
    }

    @Override
    @Transactional
    public void payInstalment(String trxId, MultipartFile multipartFile) {
        LoanTransaction transaction = findByIdOrThrowNotFound(trxId);

        List<LoanTransactionDetail> loanTransactionDetails = transaction.getLoanTransactionDetails()
                .stream().filter(ltd -> ltd.getLoanStatus().equals(UNPAID)).collect(Collectors.toList());

        if (loanTransactionDetails.isEmpty()) throw new NotAcceptableException("no more transaction");

        loanTransactionDetailService.payInstalment(loanTransactionDetails.get(0), multipartFile);
    }

    @Override
    public TransactionResponse rejectingTransaction(String trxId, Authentication authentication) {
        UserResponse userResponse = userService.getByToken(authentication);
        LoanTransaction loanTransaction = findByIdOrThrowNotFound(trxId);
        if (loanTransaction.getApprovalStatus().equals(APPROVED)) throw new NotAcceptableException("transaction already approved");
        loanTransaction.setApprovalStatus(REJECTED);
        loanTransaction.setRejectedBy(userResponse.getEmail());
        loanTransaction.setRejectedAt(System.currentTimeMillis());
        LoanTransaction save = loanTransactionRepository.save(loanTransaction);
        return new TransactionResponse(save);
    }

    private LoanTransaction findByIdOrThrowNotFound(String loanTransactionId) {
        return loanTransactionRepository.findById(loanTransactionId)
                .orElseThrow(() -> new NotFoundException(String.format("transaction not found with id %s", loanTransactionId)));
    }

    private void createLoanTransactionDetail(LoanTransaction transaction, LoanCalculation loanCalculation) {
        LoanTransactionDetail loanTransactionDetail = new LoanTransactionDetail();
        loanTransactionDetail.setLoanTransaction(transaction);
        loanTransactionDetail.setNominal(loanCalculation.calculate());
        loanTransactionDetail.setLoanStatus(UNPAID);
        loanTransactionDetailService.create(loanTransactionDetail);
    }

}
