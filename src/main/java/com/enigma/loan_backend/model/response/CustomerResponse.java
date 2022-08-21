package com.enigma.loan_backend.model.response;

import com.enigma.loan_backend.constant.Constant;
import com.enigma.loan_backend.entity.Customer;
import com.enigma.loan_backend.entity.LoanDocument;
import com.enigma.loan_backend.entity.ProfilePicture;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Getter @AllArgsConstructor @NoArgsConstructor
public class CustomerResponse {

    private String id;

    private String firstName;

    private String lastName;

    private Date dateOfBirth;

    private String phone;

    private String status;

    private String profilePicture;

    @JsonIgnoreProperties("customer")
    private List<LoanDocument> loanDocuments;

    public CustomerResponse(Customer customer) {
        this.id = customer.getId();
        this.firstName = customer.getFirstName();
        this.lastName = customer.getLastName();
        this.dateOfBirth = customer.getDateOfBirth();
        this.phone = customer.getPhone();
        this.status = customer.getStatus();
        this.profilePicture = String.format(Constant.API_GET_AVATAR, customer.getId());
        this.loanDocuments = customer.getLoanDocuments();
    }


}
