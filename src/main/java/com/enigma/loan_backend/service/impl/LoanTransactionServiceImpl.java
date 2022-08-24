package com.enigma.loan_backend.service.impl;

import com.enigma.loan_backend.entity.LoanTransaction;
import com.enigma.loan_backend.entity.LoanTransactionDetail;
import com.enigma.loan_backend.entity.LoanType;
import com.enigma.loan_backend.entity.my_enum.EInstalmentType;
import com.enigma.loan_backend.exception.NotAcceptableException;
import com.enigma.loan_backend.exception.NotFoundException;
import com.enigma.loan_backend.model.request.LoanTransactionApprovalRequest;
import com.enigma.loan_backend.model.response.TransactionDetailResponse;
import com.enigma.loan_backend.model.response.TransactionResponse;
import com.enigma.loan_backend.model.response.UserResponse;
import com.enigma.loan_backend.repository.LoanTransactionRepository;
import com.enigma.loan_backend.service.LoanTransactionDetailService;
import com.enigma.loan_backend.service.LoanTransactionService;
import com.enigma.loan_backend.service.LoanTypeService;
import com.enigma.loan_backend.service.UserService;
import com.enigma.loan_backend.utils.LoanCalculation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

import static com.enigma.loan_backend.entity.my_enum.ApprovalStatus.APPROVED;
import static com.enigma.loan_backend.entity.my_enum.LoanStatus.UNPAID;

@Service
@RequiredArgsConstructor
public class LoanTransactionServiceImpl implements LoanTransactionService {

    private final LoanTransactionRepository loanTransactionRepository;
    private final LoanTransactionDetailService loanTransactionDetailService;
    private final LoanTypeService loanTypeService;
    private final UserService userService;

    @Override
    public TransactionResponse create(LoanTransaction loanTransaction) {
        LoanType loanType = loanTypeService.getLoanTypeById(loanTransaction.getLoanType().getId());

        if (loanTransaction.getNominal() > loanType.getMaxLoan()) throw new NotAcceptableException("nominal exceed");

        return new TransactionResponse(loanTransactionRepository.save(loanTransaction));
    }

    @Override
    @Transactional
    public TransactionResponse approvingLoanTransaction(LoanTransactionApprovalRequest request, String adminId) {
        UserResponse userResponse = userService.get(adminId);
        LoanTransaction transaction = findByIdOrThrowNotFound(request.getLoanTransactionId());

        if (transaction.getApprovalStatus() != null && transaction.getApprovalStatus().equals(APPROVED)) throw new NotAcceptableException("Transaction already proceed");

        EInstalmentType instalmentType = transaction.getInstalmentType().getInstalmentType();
        LoanCalculation loanCalculation = new LoanCalculation(transaction.getNominal(), request.getInterestRates());

        for (int i = 0; i < instalmentType.getNumber(); i++) {
            createLoanTransactionDetail(transaction, loanCalculation);
        }

        transaction.setApprovalStatus(APPROVED);
        transaction.setApprovedBy(userResponse.getEmail());
        transaction.setApprovedAt(System.currentTimeMillis());

        return new TransactionResponse(transaction);
    }

    @Override
    public TransactionDetailResponse get(String id) {
        return loanTransactionDetailService.get(id);
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

    private LoanTransaction findByIdOrThrowNotFound(String loanTransactionId) {
        return loanTransactionRepository.findById(loanTransactionId)
                .orElseThrow(() -> new NotFoundException(String.format("transaction not found with id %s", loanTransactionId)));
    }

    private void createLoanTransactionDetail(LoanTransaction transaction, LoanCalculation loanCalculation) {
        LoanTransactionDetail loanTransactionDetail = new LoanTransactionDetail();
        loanTransactionDetail.setLoanTransaction(transaction);
        loanTransactionDetail.setNominal(loanCalculation.calculate());
        loanTransactionDetail.setLoanStatus(UNPAID);
    }

}
