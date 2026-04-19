package com.example.BankingApp.Repository;

import com.example.BankingApp.Model.Account;
import com.example.BankingApp.Model.Statement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StatementsRepository extends JpaRepository<Statement, Integer> {

    

}
