package com.techlabs.service;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.techlabs.dao.AccountDAO;
import com.techlabs.dao.BeneficiaryDAO;
import com.techlabs.dao.TransactionDAO;
import com.techlabs.model.Account;
import com.techlabs.model.Transaction;
import com.techlabs.util.DatabaseConnection;

public class TransactionService {
    private TransactionDAO transactionDAO = new TransactionDAO();
    private AccountDAO accountDAO = new AccountDAO();
    private BeneficiaryDAO beneficiaryDAO = new BeneficiaryDAO();
    
    public boolean transferMoney(int fromAccountId, String toAccountNumber, BigDecimal amount, String description, int userId) {
        // Validate amount
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }
        
        // Get accounts
        Account fromAccount = accountDAO.getAccountById(fromAccountId);
        Account toAccount = accountDAO.getAccountByNumber(toAccountNumber);
        
        // Validate accounts
        if (fromAccount == null || toAccount == null) {
            return false;
        }
        
        // Check if recipient is a beneficiary
        if (!beneficiaryDAO.isBeneficiary(userId, toAccountNumber)) {
            return false;
        }
        
        // Check sufficient balance
        if (fromAccount.getBalance().compareTo(amount) < 0) {
            return false;
        }
        
        // Perform transfer
        Connection connection = null;
        try {
            connection = DatabaseConnection.getConnection();
            connection.setAutoCommit(false);
            
            // Update balances
            BigDecimal newFromBalance = fromAccount.getBalance().subtract(amount);
            BigDecimal newToBalance = toAccount.getBalance().add(amount);
            
            accountDAO.updateBalance(fromAccountId, newFromBalance);
            accountDAO.updateBalance(toAccount.getAccountId(), newToBalance);
            
            // Create transaction record
            Transaction transaction = new Transaction();
            transaction.setFromAccountId(fromAccountId);
            transaction.setToAccountId(toAccount.getAccountId());
            transaction.setTransactionType("TRANSFER");
            transaction.setAmount(amount);
            transaction.setDescription(description);
            
            transactionDAO.createTransaction(transaction);
            
            connection.commit();
            return true;
            
        } catch (SQLException e) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (connection != null) {
                    connection.setAutoCommit(true);
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public List<Transaction> getTransactionsByUserId(int userId) {
        return transactionDAO.getTransactionsByUserId(userId);
    }
    
    public List<Transaction> getAllTransactions() {
        return transactionDAO.getAllTransactions();
    }
}