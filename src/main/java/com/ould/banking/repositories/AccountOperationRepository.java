package com.ould.banking.repositories;

import com.ould.banking.entities.AccountOperation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountOperationRepository extends JpaRepository<AccountOperation , Long > {
}
