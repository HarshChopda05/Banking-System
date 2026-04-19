package com.example.BankingApp.Controller;

import com.example.BankingApp.DTO.TransferMoneyRequest;
import com.example.BankingApp.Service.StatementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/statement")
public class StatementController {

    @Autowired
    StatementService statementService;

    @PutMapping("/transferMoney/{fromAcctNumber}")
    public void transferMoney(@RequestBody TransferMoneyRequest amount, @PathVariable long fromAcctNumber){
        statementService.transferMoney(amount,fromAcctNumber);

    }
}
