package com.ould.banking.web;

import com.ould.banking.dtos.*;
import com.ould.banking.exceptions.BalanceNotSufficientException;
import com.ould.banking.exceptions.BankAccountNotFoundException;
import com.ould.banking.services.AccountOperationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin("*")

public class AccountOperationRestAPI {
    private AccountOperationService accountOperationService;

    @GetMapping("/accounts/{accountId}/operations ")
    public List<AccountOperationDTO> getHistory(@PathVariable String accountId){
        return accountOperationService.accountHistory(accountId);
    }

    @GetMapping("/accounts/{accountId}/pageOperations")
    public AccountHistoryDTO getAccountHistory(
            @PathVariable String accountId,
            @RequestParam (name = "page" ,defaultValue = "0") int page,
            @RequestParam (name = "size" ,defaultValue = "5") int size) throws BankAccountNotFoundException {
        return accountOperationService.getAccountHistory(accountId,page, size);
    }
    @PostMapping("/accounts/debit")
    public DebitDTO debit(@RequestBody DebitDTO debitDTO) throws BankAccountNotFoundException, BalanceNotSufficientException {
        this.accountOperationService.debit(debitDTO.getAccountId(),debitDTO.getAmount(),debitDTO.getDescription());
        return debitDTO;
    }
    @PostMapping("/accounts/credit")
    public CreditDTO credit(@RequestBody CreditDTO creditDTO) throws BankAccountNotFoundException {
        this.accountOperationService.credit(creditDTO.getAccountId(),creditDTO.getAmount(),creditDTO.getDescription());
        return creditDTO;
    }

    @PostMapping("/accounts/transfer")
    public void transfer(@RequestBody TransfertRequestDTO transfertRequestDTO) throws BankAccountNotFoundException, BalanceNotSufficientException {
        this.accountOperationService.transfert(transfertRequestDTO.getAccountIdSource(),transfertRequestDTO.getAccountIdDestination(),transfertRequestDTO.getAmount(),transfertRequestDTO.getDescription());
    }




}
