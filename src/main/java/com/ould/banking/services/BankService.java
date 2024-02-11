package com.ould.banking.services;

import com.ould.banking.entities.BankAccount;
import com.ould.banking.entities.CurrentAccount;
import com.ould.banking.entities.SavingAccount;
import com.ould.banking.repositories.BankAccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BankService {
    private BankAccountRepository bankAccountRepository;
    public BankService(BankAccountRepository bankAccountRepository){
        this.bankAccountRepository=bankAccountRepository;
    }
    public void consulter(){
        BankAccount bankAccount=bankAccountRepository.findById("5752a0cf-840c-468a-a9c4-2000c506dc80").get();
        System.out.println("***********************");
        System.out.println(bankAccount.getBalance());
        System.out.println(bankAccount.getStatus());
        System.out.println(bankAccount.getDateCreation());
        System.out.println(bankAccount.getDateExpiration());
        System.out.println(bankAccount.getCustomer().getLastName());
        System.out.println(bankAccount.getClass().getSimpleName());
        if (bankAccount instanceof CurrentAccount){
            System.out.println("Over Draft =>"+((CurrentAccount) bankAccount).getOverDraft());
        } else if (bankAccount instanceof SavingAccount) {
            System.out.println("Interest Rate =>"+((SavingAccount) bankAccount).getInterestRate());

        }
        bankAccount.getAccountOperations().forEach(op ->{
            System.out.println(op.getOperationType() +"\t"+ op.getOperationDate() +"\t"+ op.getAmount());
        });

    }
}
