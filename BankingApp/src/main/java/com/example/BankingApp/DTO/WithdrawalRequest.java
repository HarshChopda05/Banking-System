package com.example.BankingApp.DTO;

public class WithdrawalRequest {

    private Double amount;


    public WithdrawalRequest(){
    }
    public WithdrawalRequest(Double amount) {
        this.amount = amount;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

}
