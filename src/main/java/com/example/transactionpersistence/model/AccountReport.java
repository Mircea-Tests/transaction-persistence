package com.example.transactionpersistence.model;

import model.Account;

import java.util.List;

public class AccountReport {

    private Account account;
    private List<ReportTransactionType> reportTransactionType;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public List<ReportTransactionType> getReportTransactionType() {
        return reportTransactionType;
    }

    public void setReportTransactionType(List<ReportTransactionType> reportTransactionType) {
        this.reportTransactionType = reportTransactionType;
    }
}
