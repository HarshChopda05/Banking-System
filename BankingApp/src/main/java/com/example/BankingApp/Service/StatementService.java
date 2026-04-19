package com.example.BankingApp.Service;

import com.example.BankingApp.DTO.TransferMoneyRequest;
import com.example.BankingApp.Model.Account;
import com.example.BankingApp.Model.Statement;
import com.example.BankingApp.Repository.AccountRepository;
import com.example.BankingApp.Repository.StatementsRepository;
import com.example.BankingApp.TransactionType;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class StatementService {

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    StatementsRepository statementsRepository;

    @Transactional //when any transaction will be fail that time it is automatically Rollback.....
    public void transferMoney(TransferMoneyRequest amount, long fromAcctNumber) {
        //fetch and check fromAccountNumber is available or not
        Account fromAccount = accountRepository.findById(fromAcctNumber)
                .orElseThrow(() -> new RuntimeException("Sender's Account is not found!!"));


        //fetch and check toAccountNumber is available or not
        Account toAccount = accountRepository.findById(amount.getToAccountNumber())
                .orElseThrow(() -> new RuntimeException("Receiver's account is not found!"));


        if (amount.getAmount() > fromAccount.getBalance()){
            throw new RuntimeException("Sorry you cant's Transfer money, Insufficient balance");
        }else if (amount.getAmount() <= 0){
            throw  new RuntimeException("Invalid Amount");
        }else {
            fromAccount.setBalance(fromAccount.getBalance() - amount.getAmount()); //Deduct
            toAccount.setBalance(toAccount.getBalance() + amount.getAmount());   //Credit
        }
        accountRepository.save(fromAccount); //save fromAccount
        accountRepository.save(toAccount); //save toAccount


        //Create a Debit Statement (Sender)
        Statement debitStatement = new Statement();
        debitStatement.setFromAccount(fromAccount);
        debitStatement.setToAccount(toAccount);
        debitStatement.setAmount(amount.getAmount());
        debitStatement.setType(TransactionType.WITHDRAW);  // Debit
        debitStatement.setDate(new Date()); //Date

        //Save Statement Repository
        statementsRepository.save(debitStatement);

        // Create Credit Statement (Receiver)
        Statement creditStatement = new Statement();
        creditStatement.setFromAccount(toAccount);  // Reverse
        creditStatement.setToAccount(fromAccount);  // Reverse
        creditStatement.setAmount(amount.getAmount());
        creditStatement.setType(TransactionType.DEPOSIT);   // Credit
        creditStatement.setDate(new Date());   //Date

        //Save Statement Repository
        statementsRepository.save(creditStatement);

    }
}