package com.ould.banking.web;

import com.ould.banking.dtos.BankAccountDTO;
import com.ould.banking.dtos.CurrentAccountDTO;
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

    @PostMapping("/currentAccount")
    public CurrentAccountDTO saveCurrentAccount( @RequestBody  Double initialBalance,
                                                 @RequestBody  Double overDraft,
                                                 @RequestBody Long customerId,
                                                 @RequestBody  String devise) throws CustomerNotFoundException {
        return bankAccountService.saveCurrentAccount(initialBalance,overDraft,customerId,devise);

    }

    @GetMapping("accounts/customer/{customerId}")
    public List<BankAccountDTO> listCustomerBankAccounts(@PathVariable Long customerId){
        return bankAccountService.customerBankAccountsList(customerId);
    }


}
