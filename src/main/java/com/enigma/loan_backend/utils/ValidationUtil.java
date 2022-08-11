package com.enigma.loan_backend.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Set;

@Component
public class ValidationUtil {

    private final Validator validator;

    @Autowired
    public ValidationUtil(Validator validator) {
        this.validator = validator;
    }

    public <T> void validate(T obj) {
        Set<ConstraintViolation<T>> result = validator.validate(obj);
        if (result.size() != 0) {
            throw new ConstraintViolationException(result);
        }
    }

}
