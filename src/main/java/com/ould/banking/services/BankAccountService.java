package com.ould.banking.services;

import com.ould.banking.entities.BankAccount;
import com.ould.banking.entities.CurrentAccount;
import com.ould.banking.entities.Customer;
import com.ould.banking.entities.SavingAccount;
import com.ould.banking.exceptions.BalanceNotSufficientException;
import com.ould.banking.exceptions.BankAccountNotFoundException;
import com.ould.banking.exceptions.CustomerNotFoundException;

import java.util.List;

public interface BankAccountService {
    Customer saveCustomer(Customer customer);
    SavingAccount saveSavingAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundException;
    CurrentAccount saveCurrentAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException;
    BankAccount getBankAccount(String accountId) throws BankAccountNotFoundException;
    List<Customer> listCustomers();
    void debit(String accountId, double amount, String motif) throws BankAccountNotFoundException, BalanceNotSufficientException;
    void credit(String accountId, double amount, String motif) throws BankAccountNotFoundException;
    void transfert(String accountIdExp, String accountIdDest, double amount) throws BankAccountNotFoundException, BalanceNotSufficientException;
}
