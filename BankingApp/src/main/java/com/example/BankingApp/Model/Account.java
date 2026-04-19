package com.example.BankingApp.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "account")
public class Account {

    @Id
    @Column(unique = true,name = "acct_number")
    private long acctNumber;

    @Column(name = "name")
    private String name;

    @Column(name = "balance")
    private Double balance;

    //Add Primary Key
    @OneToMany(mappedBy = "fromAccount") // fromAccount: is an Account class Object from Statement class
    @JsonIgnore //Prevent from infinite loop
    private List<Statement> sentStatements; //List for store many Statements.

    //Add Primary Key
    @OneToMany(mappedBy = "toAccount")
    @JsonIgnore
    private List<Statement> receivedStatements;

    public Account(){

    }

    public long getAcctNumber() {
        return acctNumber;
    }

    public void setAcctNumber(long acctNumber) {
        this.acctNumber = acctNumber;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Statement> getSentStatements() {
        return sentStatements;
    }

    public void setSentStatements(List<Statement> sentStatements) {
        this.sentStatements = sentStatements;
    }

    public List<Statement> getReceivedStatements() {
        return receivedStatements;
    }

    public void setReceivedStatements(List<Statement> receivedStatements) {
        this.receivedStatements = receivedStatements;
    }

}
