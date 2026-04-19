package com.example.BankingApp.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

}
