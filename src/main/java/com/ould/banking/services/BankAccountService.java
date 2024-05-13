package com.ould.banking.services;

import com.ould.banking.dtos.AddCurrentAccountDTO;
import com.ould.banking.dtos.BankAccountDTO;
import com.ould.banking.dtos.CurrentAccountDTO;
import com.ould.banking.dtos.SavingAccountDTO;
import com.ould.banking.exceptions.BankAccountNotFoundException;
import com.ould.banking.exceptions.CustomerNotFoundException;

import java.util.List;

public interface BankAccountService {

    SavingAccountDTO saveSavingAccount(SavingAccountDTO savingAccountDTO) throws CustomerNotFoundException;
    CurrentAccountDTO saveCurrentAccount(AddCurrentAccountDTO currentAccountDTO) throws CustomerNotFoundException;
    BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException;
    List<BankAccountDTO> customerBankAccountsList(Long id);


    List<BankAccountDTO> bankAccountList();






}
