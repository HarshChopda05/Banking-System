package com.example.BankingApp.Model;

import com.example.BankingApp.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
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

    public Statement(){

    }
    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Account getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(Account fromAccount) {
        this.fromAccount = fromAccount;
    }

    public Account getToAccount() {
        return toAccount;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public void setToAccount(Account toAccount) {
        this.toAccount = toAccount;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
