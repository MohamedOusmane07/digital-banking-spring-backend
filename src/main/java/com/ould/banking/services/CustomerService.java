package com.ould.banking.services;

import com.ould.banking.dtos.CustomerDTO;
import com.ould.banking.exceptions.CustomerNotFoundException;

import java.util.List;

public interface CustomerService {
    CustomerDTO saveCustomer(CustomerDTO customerDTO);
    List<CustomerDTO> listCustomers();





    CustomerDTO getCustomerById(Long customerId) throws CustomerNotFoundException;

    CustomerDTO updateCustomer(CustomerDTO customerDTO) throws CustomerNotFoundException ;

    void deleteCustomer(Long customerId) throws CustomerNotFoundException;

    List<CustomerDTO> searchCustomer(String keyword);
}
