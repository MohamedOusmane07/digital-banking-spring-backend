package com.ould.banking.entities;

import com.ould.banking.enums.AccountStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE", length = 5 , discriminatorType = DiscriminatorType.STRING)
@Data @NoArgsConstructor @AllArgsConstructor
public abstract class  BankAccount {
    @Id
    private String id;
    private double balance;
    private String currency;
    private LocalDate dateCreation;
    private LocalDate dateExpiration;
    @Enumerated(EnumType.STRING)
    private AccountStatus status;
    @ManyToOne
    private Customer customer;
    @OneToMany(mappedBy = "bankAccount",fetch = FetchType.LAZY ,cascade = {CascadeType.REMOVE, CascadeType.REFRESH})
    private List <AccountOperation> accountOperations;

}
