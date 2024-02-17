package com.ould.banking.services;

import com.ould.banking.dtos.BankAccountDTO;
import com.ould.banking.dtos.CurrentAccountDTO;
import com.ould.banking.dtos.CustomerDTO;
import com.ould.banking.dtos.SavingAccountDTO;
import com.ould.banking.entities.BankAccount;
import com.ould.banking.entities.CurrentAccount;
import com.ould.banking.entities.SavingAccount;
import com.ould.banking.exceptions.BalanceNotSufficientException;
import com.ould.banking.exceptions.BankAccountNotFoundException;
import com.ould.banking.exceptions.CustomerNotFoundException;

import java.util.List;

public interface BankAccountService {

    SavingAccountDTO saveSavingAccount(double initialBalance, double interestRate, Long customerId, String devise) throws CustomerNotFoundException;
    CurrentAccountDTO saveCurrentAccount(double initialBalance, double overDraft, Long customerId, String devise) throws CustomerNotFoundException;
    BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException;


    List<BankAccountDTO> bankAccountList();






}
