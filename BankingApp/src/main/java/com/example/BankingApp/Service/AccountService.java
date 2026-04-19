package com.example.BankingApp.Service;

import com.example.BankingApp.DTO.DepositRequest;
import com.example.BankingApp.DTO.TransferRequest;
import com.example.BankingApp.DTO.WithdrawalRequest;
import com.example.BankingApp.Model.Account;
import com.example.BankingApp.Model.Statement;
import com.example.BankingApp.Repository.AccountRepository;
import com.example.BankingApp.Repository.StatementsRepository;
import com.example.BankingApp.TransactionType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Transactional(isolation = Isolation.SERIALIZABLE) //Implement Isolation, and block current transaction
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    StatementsRepository statementsRepository;

    @Transactional
    public Account createAccount(Account act) {

        // Validation for balance
        if (act.getBalance() <= 0) {
            throw new RuntimeException("Initial balance must be greater than 0");
        }

        //This checks duplicate number generated or not
        long accountNumber;
        do {
            accountNumber = generate10DigitNumber(); //Auto Generate 10 Digits Account Number.
        } while (accountRepository.existsByAcctNumber(accountNumber));

        //Validation for Name
        if (act.getName() == null || act.getName().isEmpty()) {
            throw new IllegalArgumentException("Account holder name is required");
        }

        //long accountNumber = generate10DigitNumber();

        Account account = new Account();
        account.setAcctNumber(accountNumber);
        account.setName(act.getName());
        account.setBalance(act.getBalance());

        return accountRepository.save(account);

    }

    public long generate10DigitNumber() {
        long min = 1_000_000_000L; //10 Digit Start
        long max = 9_999_999_999L; //10 Digit End
        return min + (long) (Math.random() * (max - min));
    }

    //Show all Holders
    public List<Account> showAllAcctHolders() {
        return accountRepository.findAll();
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    //Get Balance and change it according then after change it again.
    public void withdrawMoney(WithdrawalRequest amount, long acctNumber) throws Exception {
        Account acct = accountRepository.findByIdForUpdate(acctNumber); //Prevents from double withdrawal problem.

        if (acct.getBalance() < amount.getAmount()) {
            throw new RuntimeException("Sorry, you can't withdraw money, Insufficient Balance!");
        }else if(amount.getAmount() == 0){
            throw new Exception("Please, enter amount greater than 0.");
        }else if(amount.getAmount() <= 0){
            throw new Exception("Please, enter positive amount!.");
        }else {
            acct.setBalance(acct.getBalance() - amount.getAmount());
        }
        //acct.setBalance(acct.getBalance() - amount.getAmount());

        accountRepository.save(acct); //save changes in Account table

//        // Force failure AFTER balance update
//        if (true) {
//            throw new RuntimeException("Testing rollback");
//            }

        //Add record in a statement table for withdraw money
        // Create Statement Entry
        Statement statement = new Statement();
        statement.setFromAccount(acct);   // Money going out
        statement.setToAccount(null);     // No receiver
        statement.setAmount(amount.getAmount());
        statement.setType(TransactionType.WITHDRAW);
        statement.setDate(new Date());

        statementsRepository.save(statement); //Save changes in Account table
    }
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void depositMoney(DepositRequest amount, long acctNumber) {

        Account acct = accountRepository.findByIdForUpdate(acctNumber);

        if (amount.getAmount() <= 0) {
            throw new RuntimeException("Please, enter valid Amount.");
        }else {
            acct.setBalance(acct.getBalance() + amount.getAmount());
        }
        accountRepository.save(acct); //save changes

        //Add record in a statement table for withdraw money
        // Create Statement Entry
        Statement statement = new Statement();
        statement.setFromAccount(null);  // No sender
        statement.setToAccount(acct);    // Money coming in
        statement.setAmount(amount.getAmount());
        statement.setType(TransactionType.DEPOSIT);
        statement.setDate(new Date());

        statementsRepository.save(statement);

    }
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void transferMoney(TransferRequest request) {

        if (request.getAmount() <= 0) {
            throw new RuntimeException("Invalid transfer amount");
        }

        if (request.getFromAccount() == request.getToAccount()) {
            throw new RuntimeException("Cannot transfer to same account");
        }

        // 🔥 Deadlock Prevention: Always lock in order
        long firstLock = Math.min(request.getFromAccount(), request.getToAccount());
        long secondLock = Math.max(request.getFromAccount(), request.getToAccount());

        Account first = accountRepository.findByIdForUpdate(firstLock);
        Account second = accountRepository.findByIdForUpdate(secondLock);

        if (first == null || second == null) {
            throw new RuntimeException("One or both accounts not found");
        }

        // Map correct accounts
        Account from = (first.getAcctNumber() == request.getFromAccount()) ? first : second;
        Account to   = (first.getAcctNumber() == request.getToAccount()) ? first : second;

        // 💣 Validation
        if (from.getBalance() < request.getAmount()) {
            throw new RuntimeException("Insufficient balance");
        }

        // 💰 Core Logic
        from.setBalance(from.getBalance() - request.getAmount());
        to.setBalance(to.getBalance() + request.getAmount());

        // Save both
        accountRepository.save(from);
        accountRepository.save(to);

        // 🧾 Statement Entry (Double Entry System)
        Statement statement = new Statement();
        statement.setFromAccount(from);
        statement.setToAccount(to);
        statement.setAmount(request.getAmount());
        statement.setType(TransactionType.TRANSFER);
        statement.setDate(new Date());


        statementsRepository.save(statement);

    }

    public Account showBalance(long acctNumber) {
        return accountRepository.findById(acctNumber).orElseThrow(() -> new RuntimeException("Account not found!!"));
    }

}
