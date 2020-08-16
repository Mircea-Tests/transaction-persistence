package com.example.transactionpersistence.service;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import com.example.transactionpersistence.repository.AccountRepository;
import model.Account;
import model.Transaction;
import com.example.transactionpersistence.repository.TransactionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TransactionsService {

    TransactionsRepository transactionsRepository;
    AccountRepository accountRepository;

    @Autowired
    TransactionsService(AmazonDynamoDB amazonDynamoDB, TransactionsRepository transactionsRepository,
                        AccountRepository accountRepository) {
        this.transactionsRepository = transactionsRepository;
        this.accountRepository = accountRepository;
        // We create the Transactions and Accounts tables if they don't exist
        DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);
        CreateTableRequest tableRequest = dynamoDBMapper.generateCreateTableRequest(Transaction.class);
        tableRequest.setProvisionedThroughput(new ProvisionedThroughput(1L, 1L));
        TableUtils.createTableIfNotExists(amazonDynamoDB, tableRequest);
        tableRequest = dynamoDBMapper.generateCreateTableRequest(Account.class);
        tableRequest.setProvisionedThroughput(new ProvisionedThroughput(1L, 1L));
        TableUtils.createTableIfNotExists(amazonDynamoDB, tableRequest);
    }

    @KafkaListener(topics = "transactions", groupId = "consumer")
    public void listen(Transaction transaction) {
        transactionsRepository.save(transaction);
        // We add the accounts to database if missing just to populate the database for report testing
        // otherwise we would create a controller to add/edit accounts
        if (accountRepository.findByCnp(transaction.getPayer().getCnp()) == null) {
            accountRepository.save(transaction.getPayer());
        }
        if (accountRepository.findByCnp(transaction.getPayee().getCnp()) == null) {
            accountRepository.save(transaction.getPayee());
        }
    }
}
