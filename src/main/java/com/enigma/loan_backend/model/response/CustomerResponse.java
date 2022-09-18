package com.enigma.loan_backend.model.response;

import com.enigma.loan_backend.constant.Constant;
import com.enigma.loan_backend.entity.Customer;
import com.enigma.loan_backend.entity.LoanDocument;
import com.enigma.loan_backend.entity.ProfilePicture;
import com.enigma.loan_backend.entity.my_enum.CustomerStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
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

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateOfBirth;

    private String phone;

    private CustomerStatus status;

    private FileResponse profilePicture;

    @JsonIgnoreProperties("customer")
    private List<LoanDocument> loanDocuments;

    private String userId;

    public CustomerResponse(Customer customer) {
        this.id = customer.getId();
        this.firstName = customer.getFirstName();
        this.lastName = customer.getLastName();
        this.dateOfBirth = customer.getDateOfBirth();
        this.phone = customer.getPhone();
        this.status = customer.getStatus();
        this.profilePicture = customer.getProfilePicture() != null ? new FileResponse(customer.getProfilePicture().getId(), customer.getProfilePicture().getName(), String.format(Constant.API_GET_AVATAR, customer.getId())) : null;
        this.loanDocuments = customer.getLoanDocuments();
        this.userId = customer.getUser().getId();
    }


}
