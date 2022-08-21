package com.enigma.loan_backend.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @AllArgsConstructor @NoArgsConstructor
public class CommonResponse<T> {

    private Integer code;

    private String status;

    private String message;

    private T data;

}
