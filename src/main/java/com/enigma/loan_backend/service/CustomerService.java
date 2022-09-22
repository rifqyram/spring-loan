package com.enigma.loan_backend.service;


import com.enigma.loan_backend.entity.Customer;
import com.enigma.loan_backend.model.request.CustomerUpdateRequest;
import com.enigma.loan_backend.model.response.CustomerResponse;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface CustomerService {

    Customer saveCustomer(Customer customer);
    CustomerResponse getCustomerById(String id);
    Customer get(String id);
    CustomerResponse getByToken(Authentication authentication);
    List<CustomerResponse> getCustomerList();

    CustomerResponse updateCustomer(CustomerUpdateRequest request);

    void deleteCustomer(String id);
    void deleteProfilePicture(String id);

}
