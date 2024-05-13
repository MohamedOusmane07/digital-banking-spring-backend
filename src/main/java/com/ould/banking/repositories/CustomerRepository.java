package com.ould.banking.repositories;

import com.ould.banking.dtos.CustomerDTO;
import com.ould.banking.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer ,Long> {

    //List<Customer> findByFirstNameContains(String keyword);
    @Query("select cust from Customer as cust where cust.firstName like :kw")
    List<Customer> searchCustomer(@Param("kw") String keyword);
}
