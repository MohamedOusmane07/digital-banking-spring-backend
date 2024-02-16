package com.ould.banking.web;

import com.ould.banking.services.CustomerService;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class BankAccountRestAPI {
    private CustomerService customerService;
    public BankAccountRestAPI(CustomerService customerService){
        this.customerService = customerService;
    }




}
