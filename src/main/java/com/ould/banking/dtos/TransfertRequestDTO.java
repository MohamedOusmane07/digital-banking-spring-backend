package com.ould.banking.dtos;

import lombok.Data;

@Data
public class TransfertRequestDTO {
    private String accountIdSource;
    private String accountIdDestination;
    private double amount;
    private String description;
}
