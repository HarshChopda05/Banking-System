package com.example.BankingApp.DTO;

public class TransferMoneyRequest {

    Double amount;
    long toAccountNumber;


    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public long getToAccountNumber() {
        return toAccountNumber;
    }

    public void setToAccountNumber(long toAccountNumber) {
        this.toAccountNumber = toAccountNumber;
    }
}
