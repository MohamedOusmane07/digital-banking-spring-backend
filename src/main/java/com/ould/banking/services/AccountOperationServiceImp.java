package com.ould.banking.services;

import com.ould.banking.dtos.AccountOperationDTO;
import com.ould.banking.entities.AccountOperation;
import com.ould.banking.entities.BankAccount;
import com.ould.banking.enums.OperationType;
import com.ould.banking.exceptions.BalanceNotSufficientException;
import com.ould.banking.exceptions.BankAccountNotFoundException;
import com.ould.banking.mappers.AccountOperationMapperImp;
import com.ould.banking.mappers.BankAccountMapperImp;
import com.ould.banking.mappers.CustomerMapperImp;
import com.ould.banking.repositories.AccountOperationRepository;
import com.ould.banking.repositories.BankAccountRepository;
import com.ould.banking.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AccountOperationServiceImp implements AccountOperationService{
    private CustomerRepository customerRepository;
    private AccountOperationRepository accountOperationRepository;
    private BankAccountRepository bankAccountRepository;
    private CustomerMapperImp customerMapper;
    private BankAccountMapperImp bankAccountMapper;
    private AccountOperationMapperImp operationMapperImp;
    private BankAccountService bankAccountService;
    private CustomerService customerService;
    @Override
    public void debit(String accountId, double amount, String motif) throws BankAccountNotFoundException, BalanceNotSufficientException {
        BankAccount bankAccount= bankAccountRepository.findById(accountId)
                .orElseThrow(()->new BankAccountNotFoundException("BankAccount not found"));
        if (bankAccount.getBalance()<amount){
            throw new BalanceNotSufficientException ("Balance not Sufficient");
        }
        AccountOperation accountOperation=new AccountOperation();
        accountOperation.setOperationDate(new Date());
        accountOperation.setOperationType(OperationType.DEBIT);
        accountOperation.setMotif(motif);
        accountOperation.setAmount(amount);
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepository.save(accountOperation);

        bankAccount.setBalance(bankAccount.getBalance()-amount);
        bankAccountRepository.save(bankAccount);

    }

    @Override
    public void credit(String accountId, double amount, String motif) throws BankAccountNotFoundException {
        BankAccount bankAccount= bankAccountRepository.findById(accountId)
                .orElseThrow(()->new BankAccountNotFoundException("BankAccount not found"));
        AccountOperation accountOperation=new AccountOperation();
        accountOperation.setOperationType(OperationType.CREDIT);
        accountOperation.setOperationDate(new Date());
        accountOperation.setMotif(motif);
        accountOperation.setAmount(amount);
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance()+amount);
        bankAccountRepository.save(bankAccount);


    }

    @Override
    public void transfert(String accountIdExp, String accountIdDest, double amount) throws BankAccountNotFoundException, BalanceNotSufficientException {
        debit(accountIdExp,amount,"Transfer to"+accountIdDest);
        credit(accountIdDest,amount,"Transfer from"+accountIdExp) ;
    }
    @Override
    public List<AccountOperationDTO> accountHistory(String accountId){
        List<AccountOperation> operationList=accountOperationRepository.findByBankAccountId(accountId);
        return operationList.stream()
                .map(accountOperation -> operationMapperImp.fromAccountOperation(accountOperation))
                .collect(Collectors.toList());

    }
}
