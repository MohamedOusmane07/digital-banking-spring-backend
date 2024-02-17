package com.ould.banking.dtos;


import com.ould.banking.enums.AccountStatus;
import lombok.Data;
import java.time.LocalDate;

@Data
public class SavingAccountDTO extends BankAccountDTO{

    private String id;
    private double balance;
    private String currency;
    private LocalDate dateCreation;
    private LocalDate dateExpiration;
    private AccountStatus status;
    private CustomerDTO customerDTO;
    private double interestRate;

}
