package com.ould.banking.services;

import com.ould.banking.exceptions.BalanceNotSufficientException;
import com.ould.banking.exceptions.BankAccountNotFoundException;

public interface AccountOperationService {

    void debit(String accountId, double amount, String motif) throws BankAccountNotFoundException, BalanceNotSufficientException;
    void credit(String accountId, double amount, String motif) throws BankAccountNotFoundException;
    void transfert(String accountIdExp, String accountIdDest, double amount) throws BankAccountNotFoundException, BalanceNotSufficientException;
}

