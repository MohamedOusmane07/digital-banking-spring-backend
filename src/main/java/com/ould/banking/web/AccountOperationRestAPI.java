package com.ould.banking.web;

import com.ould.banking.dtos.AccountHistoryDTO;
import com.ould.banking.dtos.AccountOperationDTO;
import com.ould.banking.exceptions.BankAccountNotFoundException;
import com.ould.banking.services.AccountOperationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class AccountOperationRestAPI {
    private AccountOperationService accountOperationService;

    @GetMapping("/accounts/{accountId}/operations")
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
}
