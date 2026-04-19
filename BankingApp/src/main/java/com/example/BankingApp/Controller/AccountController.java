package com.example.BankingApp.Controller;

import com.example.BankingApp.DTO.DepositRequest;
import com.example.BankingApp.DTO.TransferRequest;
import com.example.BankingApp.DTO.WithdrawalRequest;
import com.example.BankingApp.Model.Account;
import com.example.BankingApp.Service.AccountService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.ResourceBundle;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    AccountService accountService;

    //Create an account
    @PostMapping("/openAccount")
    public Account createAccount(@RequestBody Account act){
        return accountService.createAccount(act);
    }

    @GetMapping("/showAll")
    public List<Account> showAllAcctHolders(){
        return accountService.showAllAcctHolders();
    }

    //Withdraw Money
    @PatchMapping("/withdraw/{acct_number}")
    public ResponseEntity<String> withdraw(@RequestBody WithdrawalRequest amount, @PathVariable long acct_number) throws Exception {
        try {
            accountService.withdrawMoney(amount, acct_number);
            return ResponseEntity.ok("Withdrawal successful.");
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage()); //it shows error message in Body of Postman.
        }catch (Exception e){
            return ResponseEntity.status(500).body("Something went wrong");
        }
    }
    //Transfer Money
    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestBody TransferRequest request) {
        try {
            accountService.transferMoney(request);
            return ResponseEntity.ok("Transfer successful");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Transfer failed");
        }
    }

    //Deposit Money
    @PatchMapping("/deposit/{acct_number}")
    public void deposit(@RequestBody DepositRequest amount, @PathVariable long acct_number){
        accountService.depositMoney(amount, acct_number);
    }

    //Show Balance. FetchAll
    @GetMapping("/showBalance/{acct_number}")
    public Account showBalance(@PathVariable long acct_number){
      return accountService.showBalance(acct_number);
    }



}
