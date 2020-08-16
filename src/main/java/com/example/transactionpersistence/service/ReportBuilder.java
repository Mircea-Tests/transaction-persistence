package com.example.transactionpersistence.service;

import com.example.transactionpersistence.model.AccountReport;
import com.example.transactionpersistence.model.ReportTransactionType;
import com.example.transactionpersistence.repository.TransactionsRepository;
import model.Account;
import model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportBuilder {

    TransactionsRepository transactionsRepository;

    @Autowired
    ReportBuilder(TransactionsRepository transactionsRepository) {
        this.transactionsRepository = transactionsRepository;
    }

    public AccountReport getTransactionsByCNP(Account account) {
        AccountReport accountReport = new AccountReport();
        accountReport.setAccount(account);
        List<Transaction> transactions = new ArrayList<>();
        transactions.addAll(transactionsRepository.findByPayee(account));
        transactions.addAll(transactionsRepository.findByPayer(account));
        List<ReportTransactionType> reportTransactionTypeList = new ArrayList<>();
        for (Transaction.TransactionType transactionType : Transaction.TransactionType.values()) {
            ReportTransactionType reportTransactionType = new ReportTransactionType();
            reportTransactionType.setType(transactionType.getName());
            List<Transaction> transactionsByType = transactions.stream()
                    .filter(transaction -> transaction.getType().equals(transactionType))
                    .collect(Collectors.toList());
            reportTransactionType.setTransactions(transactionsByType);
            reportTransactionType.setSum(transactionsByType.stream().mapToDouble(Transaction::getAmount).sum());
            reportTransactionTypeList.add(reportTransactionType);
        }
        accountReport.setReportTransactionType(reportTransactionTypeList);
        return accountReport;
    }
}
