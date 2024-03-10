package com.ould.banking.dtos;

import com.ould.banking.enums.AccountStatus;
import lombok.*;
import java.time.LocalDate;

@Getter @Setter @Builder @ToString
@AllArgsConstructor @NoArgsConstructor
public class CurrentAccountDTO extends BankAccountDTO {


    private String id;
    private double balance;
    private String currency;
    private LocalDate dateCreation;
    private LocalDate dateExpiration;
    private AccountStatus status;
    private CustomerDTO customerDTO;
    private double overDraft;


}
