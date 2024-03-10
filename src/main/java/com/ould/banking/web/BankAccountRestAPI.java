package com.ould.banking.web;

import com.ould.banking.dtos.BankAccountDTO;
import com.ould.banking.dtos.CurrentAccountDTO;
import com.ould.banking.dtos.SavingAccountDTO;
import com.ould.banking.exceptions.BankAccountNotFoundException;
import com.ould.banking.exceptions.CustomerNotFoundException;
import com.ould.banking.services.BankAccountService;
import com.ould.banking.services.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@CrossOrigin("*")
public class BankAccountRestAPI {
    private BankAccountService bankAccountService;
    public BankAccountRestAPI(BankAccountService bankAccountService){
        this.bankAccountService = bankAccountService;

    }
    @GetMapping("/accounts/{accountId}")
    public BankAccountDTO getBankAccount(@PathVariable String accountId) throws BankAccountNotFoundException {
        return bankAccountService.getBankAccount(accountId);
    }

    @GetMapping("/accounts")
    public List<BankAccountDTO> listAccount(){
        return bankAccountService.bankAccountList();
    }

    @PostMapping("/currentAccount/add")
    public CurrentAccountDTO saveCurrentAccount( @RequestBody CurrentAccountDTO currentAccountDTO) throws CustomerNotFoundException {
        return bankAccountService.saveCurrentAccount(currentAccountDTO);

    }
    @PostMapping("/savingAccount/add")
    public SavingAccountDTO saveSavingAccount(@RequestBody SavingAccountDTO savingAccountDTO) throws CustomerNotFoundException {
        return bankAccountService.saveSavingAccount(savingAccountDTO);
    }

    @GetMapping("accounts/customer/{customerId}")
    public List<BankAccountDTO> listCustomerBankAccounts(@PathVariable Long customerId){
        return bankAccountService.customerBankAccountsList(customerId);
    }


}
