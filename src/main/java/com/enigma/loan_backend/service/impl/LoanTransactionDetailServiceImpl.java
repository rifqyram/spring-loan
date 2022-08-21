package com.enigma.loan_backend.service.impl;

import com.enigma.loan_backend.entity.GuaranteePicture;
import com.enigma.loan_backend.entity.LoanTransactionDetail;
import com.enigma.loan_backend.entity.my_enum.LoanStatus;
import com.enigma.loan_backend.exception.NotFoundException;
import com.enigma.loan_backend.model.response.FileResponse;
import com.enigma.loan_backend.model.response.TransactionDetailResponse;
import com.enigma.loan_backend.repository.LoanTransactionDetailRepository;
import com.enigma.loan_backend.service.GuaranteePictureService;
import com.enigma.loan_backend.service.LoanTransactionDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import static com.enigma.loan_backend.entity.my_enum.LoanStatus.PAID;

@Service
@Transactional
@RequiredArgsConstructor
public class LoanTransactionDetailServiceImpl implements LoanTransactionDetailService {

    private final LoanTransactionDetailRepository loanTransactionDetailRepository;
    private final GuaranteePictureService guaranteePictureService;

    @Override
    public LoanTransactionDetail create(LoanTransactionDetail loanTransactionDetail) {
        return loanTransactionDetailRepository.save(loanTransactionDetail);
    }

    @Override
    public TransactionDetailResponse get(String id) {
        return new TransactionDetailResponse(findByIdOrThrowNotFound(id));
    }

    @Override
    public void payInstalment(LoanTransactionDetail loanTransactionDetail, MultipartFile multipartFile) {
        GuaranteePicture guaranteePicture = guaranteePictureService.save(multipartFile);
        loanTransactionDetail.setTransactionDate(System.currentTimeMillis());
        loanTransactionDetail.setGuaranteePicture(guaranteePicture);
        loanTransactionDetail.setLoanStatus(PAID);
        loanTransactionDetailRepository.save(loanTransactionDetail);
    }

    private LoanTransactionDetail findByIdOrThrowNotFound(String id) {
        return loanTransactionDetailRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format(
                "transaction detail not found with id %s",
                id
        )));
    }
}
