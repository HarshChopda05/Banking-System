package com.example.BankingApp.Model;

import com.example.BankingApp.TransactionType;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "statement")
public class Statement {

    //For Series Number.
    @Id
    @Column(name = "no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer number;

    @Column(name = "date")
    @Temporal(TemporalType.DATE)//Gives current date
    private Date date;

    //Add Foreign key FROM_ACCOUNT_NUMBER
    @ManyToOne //(cascade = CascadeType.ALL)  //Many entries should have 1 account number so ManyToOne
    @JoinColumn(name = "from_acct_number",referencedColumnName = "acct_number")
    Account fromAccount; // Create an object of account for store object's address

    //Add foreign key for TO_ACCOUNT_NUMBER
    @ManyToOne //(cascade = CascadeType.ALL)
    @JoinColumn(name = "to_acct_number", referencedColumnName = "acct_number")
    Account toAccount;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private TransactionType type;

    @Column(name = "amount")
    private double amount;

}
