package com.example.BankingApp.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransferMoneyRequest {

    Double amount;
    long toAccountNumber;

}
