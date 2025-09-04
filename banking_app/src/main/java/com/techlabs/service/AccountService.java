package com.techlabs.service;

import com.techlabs.dao.AccountDAO;
import com.techlabs.model.Account;

import java.math.BigDecimal;
import java.util.List;

public class AccountService {
    private AccountDAO accountDAO = new AccountDAO();
    
    public List<Account> getAccountsByUserId(int userId) {
        return accountDAO.getAccountsByUserId(userId);
    }
    
    public Account getAccountById(int accountId) {
        return accountDAO.getAccountById(accountId);
    }
    
    public Account getAccountByNumber(String accountNumber) {
        return accountDAO.getAccountByNumber(accountNumber);
    }
    
    public boolean createAccount(Account account) {
        if (account == null) {
            return false;
        }
        return accountDAO.createAccount(account);
    }
    
    public BigDecimal getTotalBalance(int userId) {
        return accountDAO.getTotalBalance(userId);
    }
}