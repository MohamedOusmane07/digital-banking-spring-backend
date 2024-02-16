package com.ould.banking.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ould.banking.entities.BankAccount;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
public class CustomerDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;


}
