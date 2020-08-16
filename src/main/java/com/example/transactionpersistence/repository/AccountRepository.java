package com.example.transactionpersistence.repository;

import model.Account;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface AccountRepository extends CrudRepository<Account, String> {

    Account findByCnp(Long cnp);
}
