package com.example.BankingApp.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransferRequest {
    private long fromAccount;
    private long toAccount;
    private double amount;

}
