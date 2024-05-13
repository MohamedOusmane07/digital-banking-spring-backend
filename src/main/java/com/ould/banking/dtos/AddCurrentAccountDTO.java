package com.ould.banking.dtos;

import com.ould.banking.enums.AccountStatus;
import lombok.*;

import java.time.LocalDate;

@Getter @Setter @Builder @ToString
@AllArgsConstructor @NoArgsConstructor
public class AddCurrentAccountDTO extends BankAccountDTO {



    private double balance;
    private String currency;
    private AccountStatus status;
    private CustomerDTO customerDTO;
    private double overDraft;


}
