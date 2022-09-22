package com.enigma.loan_backend.service.impl;

import com.enigma.loan_backend.entity.Customer;
import com.enigma.loan_backend.entity.User;
import com.enigma.loan_backend.entity.my_enum.CustomerStatus;
import com.enigma.loan_backend.exception.NotFoundException;
import com.enigma.loan_backend.model.request.CustomerUpdateRequest;
import com.enigma.loan_backend.model.response.CustomerResponse;
import com.enigma.loan_backend.model.response.UserResponse;
import com.enigma.loan_backend.repository.CustomerRepository;
import com.enigma.loan_backend.service.CustomerService;
import com.enigma.loan_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.enigma.loan_backend.entity.my_enum.CustomerStatus.ACTIVE;
import static com.enigma.loan_backend.entity.my_enum.CustomerStatus.INACTIVE;

@Service @RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final UserService userService;

    @Override
    public Customer saveCustomer(Customer customer) {
        customer.setStatus(ACTIVE);
        return customerRepository.save(customer);
    }

    @Override
    public CustomerResponse getCustomerById(String id) {
        return new CustomerResponse(findCustomerByIdOrThrowNotFound(id));
    }

    @Override
    public Customer get(String id) {
        return findCustomerByIdOrThrowNotFound(id);
    }

    @Override
    public CustomerResponse getByToken(Authentication authentication) {
        User user = userService.findByToken(authentication);
        Customer customer = customerRepository.findByUserId(user.getId()).orElseThrow(() -> new NotFoundException("customer not found"));
        return new CustomerResponse(customer);
    }

    @Override
    public List<CustomerResponse> getCustomerList() {
        return customerRepository.findAll().stream().map(CustomerResponse::new).collect(Collectors.toList());
    }

    @Override
    public CustomerResponse updateCustomer(CustomerUpdateRequest request) {
        Customer customer = get(request.getId());
        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setDateOfBirth(request.getDateOfBirth());
        customer.setPhone(request.getPhone());
        Customer save = customerRepository.save(customer);
        return new CustomerResponse(save);
    }

    @Override
    public void deleteCustomer(String id) {
        Customer customer = get(id);
        customer.setStatus(INACTIVE);
        customerRepository.save(customer);
    }

    @Override
    public void deleteProfilePicture(String id) {
        Customer customer = findCustomerByIdOrThrowNotFound(id);
        customer.setProfilePicture(null);
        customerRepository.save(customer);
    }


    private Customer findCustomerByIdOrThrowNotFound(String id) {
        return customerRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("user with %s not found", id)));
    }
}
