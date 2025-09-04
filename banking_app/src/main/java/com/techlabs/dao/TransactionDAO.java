package com.techlabs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.techlabs.model.Transaction;
import com.techlabs.util.DatabaseConnection;

public class TransactionDAO {
    
    public boolean createTransaction(Transaction transaction) {
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            connection = DatabaseConnection.getConnection();
            String sql = "INSERT INTO transactions (from_account_id, to_account_id, transaction_type, amount, description, status) VALUES (?, ?, ?, ?, ?, ?)";
            statement = connection.prepareStatement(sql);
            
            statement.setObject(1, transaction.getFromAccountId());
            statement.setObject(2, transaction.getToAccountId());
            statement.setString(3, transaction.getTransactionType());
            statement.setBigDecimal(4, transaction.getAmount());
            statement.setString(5, transaction.getDescription());
            statement.setString(6, "SUCCESS");
            
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public List<Transaction> getTransactionsByUserId(int userId) {
        List<Transaction> transactions = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try {
            connection = DatabaseConnection.getConnection();
            String sql = "SELECT t.*, " +
                        "a1.account_number as from_account_number, " +
                        "a2.account_number as to_account_number " +
                        "FROM transactions t " +
                        "LEFT JOIN accounts a1 ON t.from_account_id = a1.account_id " +
                        "LEFT JOIN accounts a2 ON t.to_account_id = a2.account_id " +
                        "WHERE a1.user_id = ? OR a2.user_id = ? " +
                        "ORDER BY t.transaction_date DESC LIMIT 100";
            
            statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            statement.setInt(2, userId);
            resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                transactions.add(mapResultSetToTransaction(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }
    
    public List<Transaction> getAllTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try {
            connection = DatabaseConnection.getConnection();
            String sql = "SELECT t.*, " +
                        "a1.account_number as from_account_number, " +
                        "a2.account_number as to_account_number " +
                        "FROM transactions t " +
                        "LEFT JOIN accounts a1 ON t.from_account_id = a1.account_id " +
                        "LEFT JOIN accounts a2 ON t.to_account_id = a2.account_id " +
                        "ORDER BY t.transaction_date DESC LIMIT 500";
            
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                transactions.add(mapResultSetToTransaction(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }
    
    private Transaction mapResultSetToTransaction(ResultSet resultSet) throws SQLException {
        Transaction transaction = new Transaction();
        transaction.setTransactionId(resultSet.getInt("transaction_id"));
        transaction.setFromAccountId(resultSet.getObject("from_account_id", Integer.class));
        transaction.setToAccountId(resultSet.getObject("to_account_id", Integer.class));
        transaction.setTransactionType(resultSet.getString("transaction_type"));
        transaction.setAmount(resultSet.getBigDecimal("amount"));
        transaction.setDescription(resultSet.getString("description"));
        transaction.setStatus(resultSet.getString("status"));
        transaction.setTransactionDate(resultSet.getTimestamp("transaction_date"));
        
        // Extraa
        transaction.setFromAccountNumber(resultSet.getString("from_account_number"));
        transaction.setToAccountNumber(resultSet.getString("to_account_number"));
        
        return transaction;
    }
}