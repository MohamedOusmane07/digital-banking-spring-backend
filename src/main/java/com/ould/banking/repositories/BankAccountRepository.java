package com.ould.banking.repositories;

import com.ould.banking.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BankAccountRepository extends JpaRepository<BankAccount , String> {
    List<BankAccount> findBankAccountByCustomerId(Long customerId);
}
