package com.enigma.loan_backend.service;


import com.enigma.loan_backend.entity.Customer;

import java.util.List;

public interface CustomerService {

    Customer saveCustomer(Customer customer);
    Customer getCustomerById(String id);
    List<Customer> getCustomerList();
    void deleteCustomer(String id);

    void deleteProfilePicture(String id);

}
