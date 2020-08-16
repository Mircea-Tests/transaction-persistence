package com.example.transactionpersistence.model;

import model.Transaction;
import java.util.List;

public class ReportTransactionType {

    String type;
    List<Transaction> transactions;
    Double sum;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }
}
