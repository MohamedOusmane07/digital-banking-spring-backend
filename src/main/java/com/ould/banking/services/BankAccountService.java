package com.ould.banking.services;

import com.ould.banking.dtos.CustomerDTO;
import com.ould.banking.entities.BankAccount;
import com.ould.banking.entities.CurrentAccount;
import com.ould.banking.entities.SavingAccount;
import com.ould.banking.exceptions.BalanceNotSufficientException;
import com.ould.banking.exceptions.BankAccountNotFoundException;
import com.ould.banking.exceptions.CustomerNotFoundException;

import java.util.List;

public interface BankAccountService {

    SavingAccount saveSavingAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundException;
    CurrentAccount saveCurrentAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException;
    BankAccount getBankAccount(String accountId) throws BankAccountNotFoundException;


    List<BankAccount> bankAccountList();






}
