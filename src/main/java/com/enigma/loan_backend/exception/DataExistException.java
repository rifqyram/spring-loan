package com.enigma.loan_backend.exception;

public class DataExistException extends RuntimeException{
    public DataExistException(String message) {
        super(message);
    }
}
