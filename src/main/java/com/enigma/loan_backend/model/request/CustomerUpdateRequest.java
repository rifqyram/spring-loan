package com.enigma.loan_backend.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class CustomerUpdateRequest {
    private String id;

    private String firstName;

    private String lastName;

    private Date dateOfBirth;

    private String phone;

}
