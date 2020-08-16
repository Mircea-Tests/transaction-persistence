package com.example.transactionpersistence.controller;

import com.example.transactionpersistence.model.AccountReport;
import com.example.transactionpersistence.repository.AccountRepository;
import com.example.transactionpersistence.repository.TransactionsRepository;
import com.example.transactionpersistence.service.ReportBuilder;
import model.Account;
import model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
public class ReportController {

    @Autowired
    TransactionsRepository transactionsRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    ReportBuilder reportBuilder;

    @GetMapping(path = "/transactions")
    public List<Transaction> getAllTransactions() {
        return StreamSupport.stream(transactionsRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    @GetMapping(path = "/accountReport")
    public AccountReport getAccountReportByCNP(@RequestHeader Long cnp) {
        Account account = accountRepository.findByCnp(cnp);
        if (account != null) {
            return reportBuilder.getTransactionsByCNP(account);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid CNP");
    }
}
