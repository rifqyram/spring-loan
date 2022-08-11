package com.enigma.loan_backend.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class CommonResponse<T> {

    private Integer code;

    private String status;

    private String message;

    private T data;

}
