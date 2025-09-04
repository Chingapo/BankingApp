package com.techlabs.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.techlabs.model.Account;
import com.techlabs.util.DatabaseConnection;

public class AccountDAO {
    
    public List<Account> getAccountsByUserId(int userId) {
        List<Account> accounts = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try {
            connection = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM accounts WHERE user_id = ? AND is_active = true ORDER BY created_at DESC";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                accounts.add(mapResultSetToAccount(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }
    
    public Account getAccountById(int accountId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try {
            connection = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM accounts WHERE account_id = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, accountId);
            resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                return mapResultSetToAccount(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public Account getAccountByNumber(String accountNumber) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try {
            connection = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM accounts WHERE account_number = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, accountNumber);
            resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                return mapResultSetToAccount(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public boolean createAccount(Account account) {
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            connection = DatabaseConnection.getConnection();
            String sql = "INSERT INTO accounts (user_id, account_number, account_type, balance) VALUES (?, ?, ?, ?)";
            statement = connection.prepareStatement(sql);
            
            statement.setInt(1, account.getUserId());
            statement.setString(2, generateAccountNumber());
            statement.setString(3, account.getAccountType());
            statement.setBigDecimal(4, account.getBalance());
            
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean updateBalance(int accountId, BigDecimal newBalance) {
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            connection = DatabaseConnection.getConnection();
            String sql = "UPDATE accounts SET balance = ? WHERE account_id = ?";
            statement = connection.prepareStatement(sql);
            
            statement.setBigDecimal(1, newBalance);
            statement.setInt(2, accountId);
            
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public BigDecimal getTotalBalance(int userId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try {
            connection = DatabaseConnection.getConnection();
            String sql = "SELECT SUM(balance) as total FROM accounts WHERE user_id = ? AND is_active = true";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                BigDecimal total = resultSet.getBigDecimal("total");
                return total != null ? total : BigDecimal.ZERO;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return BigDecimal.ZERO;
    }
    
    private String generateAccountNumber() {
        return "ACC" + System.currentTimeMillis() % 1000000;
    }
    
    private Account mapResultSetToAccount(ResultSet resultSet) throws SQLException {
        Account account = new Account();
        account.setAccountId(resultSet.getInt("account_id"));
        account.setUserId(resultSet.getInt("user_id"));
        account.setAccountNumber(resultSet.getString("account_number"));
        account.setAccountType(resultSet.getString("account_type"));
        account.setBalance(resultSet.getBigDecimal("balance"));
        account.setActive(resultSet.getBoolean("is_active"));
        account.setCreatedAt(resultSet.getTimestamp("created_at"));
        account.setUpdatedAt(resultSet.getTimestamp("updated_at"));
        return account;
    }
}