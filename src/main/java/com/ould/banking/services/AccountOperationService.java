package com.ould.banking.services;

import com.ould.banking.dtos.AccountHistoryDTO;
import com.ould.banking.dtos.AccountOperationDTO;
import com.ould.banking.exceptions.BalanceNotSufficientException;
import com.ould.banking.exceptions.BankAccountNotFoundException;

import java.util.List;

public interface AccountOperationService {

    void debit(String accountId, double amount, String motif) throws BankAccountNotFoundException, BalanceNotSufficientException;
    void credit(String accountId, double amount, String motif) throws BankAccountNotFoundException;
    void transfert(String accountIdExp, String accountIdDest, double amount) throws BankAccountNotFoundException, BalanceNotSufficientException;

    List<AccountOperationDTO> accountHistory(String accountId);

    AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException;
}

