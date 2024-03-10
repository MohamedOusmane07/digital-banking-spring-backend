package com.ould.banking;

import com.ould.banking.dtos.BankAccountDTO;
import com.ould.banking.dtos.CurrentAccountDTO;
import com.ould.banking.dtos.CustomerDTO;
import com.ould.banking.dtos.SavingAccountDTO;
import com.ould.banking.entities.*;
import com.ould.banking.enums.AccountStatus;
import com.ould.banking.enums.OperationType;
import com.ould.banking.exceptions.BalanceNotSufficientException;
import com.ould.banking.exceptions.BankAccountNotFoundException;
import com.ould.banking.exceptions.CustomerNotFoundException;
import com.ould.banking.repositories.AccountOperationRepository;
import com.ould.banking.repositories.BankAccountRepository;
import com.ould.banking.repositories.CustomerRepository;
import com.ould.banking.services.AccountOperationService;
import com.ould.banking.services.BankAccountService;
import com.ould.banking.services.CustomerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class DigitalBankingBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(DigitalBankingBackendApplication.class, args);
    }
    //@Bean
    CommandLineRunner start(CustomerRepository customerRepository, AccountOperationRepository accountOperationRepository, BankAccountRepository bankAccountRepository){

        return args -> {
            Stream.of("Mohamed Ousmane","Nasroune Ousmane","Ibrahim Ousmane").forEach(name->{
                Customer customer=new Customer();
                List<String> names= Arrays.asList(name.split(" "));
                customer.setFirstName(names.get(1));
                customer.setLastName(names.get(0));
                customer.setEmail(names.get(0).toLowerCase()+"ousib@gmail.com");
                //System.out.println(names[1]);
                customerRepository.save(customer);

            });

            customerRepository.findAll().forEach(cust -> {
                CurrentAccount currentAccount = new CurrentAccount();
                currentAccount.setId(UUID.randomUUID().toString());
                currentAccount.setCustomer(cust);
                currentAccount.setDateCreation(LocalDate.now());
                currentAccount.setDateExpiration(LocalDate.now().plusYears(4));
                currentAccount.setBalance(Math.random()*88888);
                currentAccount.setOverDraft(5000);
                currentAccount.setCurrency("DH");
                currentAccount.setStatus(AccountStatus.CREATED);

                bankAccountRepository.save(currentAccount);
            });

            customerRepository.findAll().forEach(cust -> {
                SavingAccount savingAccount = new SavingAccount();
                savingAccount.setId(UUID.randomUUID().toString());
                savingAccount.setCustomer(cust);
                savingAccount.setDateCreation(LocalDate.now());
                savingAccount.setDateExpiration(LocalDate.now().plusYears(4));
                savingAccount.setBalance(Math.random()*60888);
                savingAccount.setInterestRate(5);
                savingAccount.setCurrency("DH");
                savingAccount.setStatus(AccountStatus.CREATED);

                bankAccountRepository.save(savingAccount);
            });

            bankAccountRepository.findAll().forEach(account->{
                for (int i = 0; i < 5; i++) {
                    AccountOperation accountOperation= new AccountOperation();
                    accountOperation.setBankAccount(account);
                    accountOperation.setAmount(Math.random()*120000);
                    accountOperation.setOperationType(Math.random()>0.5? OperationType.CREDIT : OperationType.DEBIT);
                    accountOperation.setOperationDate(new Date());
                    accountOperationRepository.save(accountOperation);

                }
            });

        };
    }
    @Bean
    CommandLineRunner commandLineRunner(BankAccountService bankAccountService, CustomerService customerService, AccountOperationService accountOperationService,BankAccountRepository bankAccountRepository){

        return args -> {
            Stream.of("Ousmane Ibrahim","Ousmane Nasroune","Ousmane Mohamed").forEach(name->{
                CustomerDTO customer=new CustomerDTO();
                List<String> names= List.of(name.split(" "));
                customer.setLastName(names.get(0));
                customer.setFirstName(names.get(1));
                customer.setEmail(names.get(1).toLowerCase()+"@gmail.com");
                customerService.saveCustomer(customer);
            });



            customerService.listCustomers().forEach(customer -> {
                try {
                  CurrentAccountDTO currentAccountDTO=CurrentAccountDTO
                          .builder()
                          .overDraft(3)
                          .balance(Math.random()*123455+1000)
                          .currency("DHH")
                          .customerDTO(customer)
                          .build();

                    bankAccountService.saveCurrentAccount(currentAccountDTO);
                    //bankAccountService.saveSavingAccount(Math.random()*120000, 5.4,customer.getId(),"DH");


                } catch (CustomerNotFoundException e) {
                    e.printStackTrace();
                }
            });

            List<BankAccountDTO> bankAccounts=bankAccountService.bankAccountList();
            for (BankAccountDTO bankAccount:bankAccounts){
                String accountId;
                if (bankAccount instanceof SavingAccountDTO){
                    accountId=((SavingAccountDTO) bankAccount).getId();
                }else {
                    accountId=((CurrentAccountDTO) bankAccount).getId();
                }
                for (int i = 0; i < 10; i++) {
                    accountOperationService.credit(accountId,10000+Math.random()*120000,"Credit");
                    accountOperationService.debit(accountId,1000+Math.random()*9000,"Debit");
                }
            }





        };
    }

}
