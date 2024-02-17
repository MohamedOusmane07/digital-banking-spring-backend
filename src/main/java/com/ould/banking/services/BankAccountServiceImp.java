package com.ould.banking.services;

import com.ould.banking.dtos.BankAccountDTO;
import com.ould.banking.dtos.CurrentAccountDTO;
import com.ould.banking.dtos.SavingAccountDTO;
import com.ould.banking.entities.BankAccount;
import com.ould.banking.entities.CurrentAccount;
import com.ould.banking.entities.Customer;
import com.ould.banking.entities.SavingAccount;
import com.ould.banking.enums.AccountStatus;
import com.ould.banking.exceptions.BankAccountNotFoundException;
import com.ould.banking.exceptions.CustomerNotFoundException;
import com.ould.banking.mappers.BankAccountMapperImp;
import com.ould.banking.mappers.CustomerMapperImp;
import com.ould.banking.repositories.AccountOperationRepository;
import com.ould.banking.repositories.BankAccountRepository;
import com.ould.banking.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BankAccountServiceImp implements BankAccountService {
    private BankAccountRepository bankAccountRepository;
    private CustomerRepository customerRepository;
    private AccountOperationRepository accountOperationRepository;
    private BankAccountMapperImp bankAccountMapper;

    @Override
    public SavingAccountDTO saveSavingAccount(double initialBalance, double interestRate, Long customerId,String devise) throws CustomerNotFoundException {
        Customer customer= customerRepository.findById(customerId).orElse(null);
        if (customer==null){
            throw new CustomerNotFoundException("Customer not found");
        }
        SavingAccount savingAccount= new SavingAccount();
        savingAccount.setId(UUID.randomUUID().toString());
        savingAccount.setDateCreation(LocalDate.now());
        savingAccount.setDateExpiration(LocalDate.now().plusYears(4));
        savingAccount.setBalance(initialBalance);
        savingAccount.setInterestRate(interestRate);
        savingAccount.setCustomer(customer);
        savingAccount.setStatus(AccountStatus.CREATED);
        savingAccount.setCurrency(devise);
        SavingAccount savedBankAccount=bankAccountRepository.save(savingAccount);

        return bankAccountMapper.fromSavingAccount(savedBankAccount);
    }


    @Override
    public CurrentAccountDTO saveCurrentAccount(double initialBalance, double overDraft, Long customerId,String devise) throws CustomerNotFoundException {
        Customer customer=customerRepository.findById(customerId).orElse(null);
        if (customer==null){
            throw new CustomerNotFoundException("Customer not found");
        }
        CurrentAccount currentAccount=new CurrentAccount();
        currentAccount.setId(UUID.randomUUID().toString());
        currentAccount.setBalance(initialBalance);
        currentAccount.setDateCreation(LocalDate.now());
        currentAccount.setDateExpiration(LocalDate.now().plusYears(4));
        currentAccount.setOverDraft(overDraft);
        currentAccount.setCustomer(customer);
        currentAccount.setCurrency(devise);
        currentAccount.setStatus(AccountStatus.CREATED);

        CurrentAccount savedBankAccount= bankAccountRepository.save(currentAccount);
        return bankAccountMapper.fromCurrentAccount(savedBankAccount);

    }


    @Override
    public BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException {
        BankAccount bankAccount=bankAccountRepository.findById(accountId)
                . orElseThrow(()->new BankAccountNotFoundException("BankAccount not found"));
        if (bankAccount instanceof SavingAccount) {
            SavingAccount savingAccount = (SavingAccount) bankAccount;

            return bankAccountMapper.fromSavingAccount(savingAccount);
        }
        else {
            CurrentAccount currentAccount=(CurrentAccount) bankAccount;
            return bankAccountMapper.fromCurrentAccount(currentAccount);
        }
    }

    @Override
    public List<BankAccountDTO> bankAccountList(){
       List<BankAccount> bankAccounts=bankAccountRepository.findAll();
       List<BankAccountDTO> bankAccountDTOS=bankAccounts.stream().map(bankAccount -> {
           if (bankAccount instanceof SavingAccount){
               SavingAccount savingAccount=(SavingAccount) bankAccount;
               return bankAccountMapper.fromSavingAccount(savingAccount);
           } else {
               CurrentAccount currentAccount=(CurrentAccount) bankAccount;
               return bankAccountMapper.fromCurrentAccount(currentAccount);
           }
       }).collect(Collectors.toList());
       return bankAccountDTOS;
    }
}
