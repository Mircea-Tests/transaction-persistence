package com.example.transactionpersistence.repository;

import model.Account;
import model.Transaction;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@EnableScan
public interface TransactionsRepository extends CrudRepository<Transaction, String> {
    List<Transaction> findByPayer(Account account);
    List<Transaction> findByPayee(Account account);
}
