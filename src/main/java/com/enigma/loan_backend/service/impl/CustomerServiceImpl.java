package com.enigma.loan_backend.service.impl;

import com.enigma.loan_backend.entity.Customer;
import com.enigma.loan_backend.exception.NotFoundException;
import com.enigma.loan_backend.repository.CustomerRepository;
import com.enigma.loan_backend.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer getCustomerById(String id) {
        return findCustomerByIdOrThrowNotFound(id);
    }

    @Override
    public List<Customer> getCustomerList() {
        return customerRepository.findAll();
    }

    @Override
    public void deleteCustomer(String id) {
        customerRepository.deleteById(id);
    }

    @Override
    public void deleteProfilePicture(String id) {
        Customer customer = getCustomerById(id);
        customer.setProfilePicture(null);
        customerRepository.save(customer);
    }


    private Customer findCustomerByIdOrThrowNotFound(String id) {
        return customerRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("user with %s not found", id)));
    }
}
