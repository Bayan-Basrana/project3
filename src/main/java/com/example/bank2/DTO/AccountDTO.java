package com.example.bank2.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AccountDTO {

    private String accountNumber;
    private Double balance;
    private Boolean isActive;

}
