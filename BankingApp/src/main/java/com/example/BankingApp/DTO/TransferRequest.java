package com.example.BankingApp.DTO;

import lombok.*;
import org.springframework.stereotype.Service;


public class TransferRequest {
    private long fromAccount;
    private long toAccount;
    private double amount;

    public TransferRequest(){

    }

    public TransferRequest(double amount, long fromAccount, long toAccount) {
        this.amount = amount;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(long fromAccount) {
        this.fromAccount = fromAccount;
    }

    public long getToAccount() {
        return toAccount;
    }

    public void setToAccount(long toAccount) {
        this.toAccount = toAccount;
    }
}
