package com.enigma.loan_backend.controller;

import com.enigma.loan_backend.exception.NotAcceptableException;
import com.enigma.loan_backend.exception.NotFoundException;
import com.enigma.loan_backend.model.response.CommonResponse;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorController {

    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ResponseEntity<Object> handleValidateException(ConstraintViolationException e) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        CommonResponse<String> webResponse = new CommonResponse<>(
                400,
                badRequest.name(),
                e.getMessage(),
                null
        );
        return new ResponseEntity<>(webResponse, badRequest);
    }

    @ExceptionHandler(value = {NotAcceptableException.class})
    public ResponseEntity<Object> handleNotAcceptableException(NotAcceptableException e) {
        HttpStatus notAcceptable = HttpStatus.NOT_ACCEPTABLE;
        CommonResponse<String> webResponse = new CommonResponse<>(
                406,
                notAcceptable.name(),
                e.getMessage(),
                null
        );
        return new ResponseEntity<>(webResponse, notAcceptable);
    }

    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(NotFoundException e) {
        HttpStatus notFound = HttpStatus.NOT_FOUND;
        CommonResponse<String> webResponse = new CommonResponse<>(
                404,
                notFound.name(),
                e.getMessage(),
                null
        );
        return new ResponseEntity<>(webResponse, notFound);
    }

}
