package com.techlabs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.techlabs.model.Beneficiary;
import com.techlabs.util.DatabaseConnection;

public class BeneficiaryDAO {
    
    public List<Beneficiary> getBeneficiariesByUserId(int userId) {
        List<Beneficiary> beneficiaries = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try {
            connection = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM beneficiaries WHERE user_id = ? AND is_active = true ORDER BY added_at DESC";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                beneficiaries.add(mapResultSetToBeneficiary(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return beneficiaries;
    }
    
    public boolean addBeneficiary(Beneficiary beneficiary) {
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            connection = DatabaseConnection.getConnection();
            String sql = "INSERT INTO beneficiaries (user_id, beneficiary_account_number, beneficiary_name, nickname) VALUES (?, ?, ?, ?)";
            statement = connection.prepareStatement(sql);
            
            statement.setInt(1, beneficiary.getUserId());
            statement.setString(2, beneficiary.getBeneficiaryAccountNumber());
            statement.setString(3, beneficiary.getBeneficiaryName());
            statement.setString(4, beneficiary.getNickname());
            
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean removeBeneficiary(int beneficiaryId) {
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            connection = DatabaseConnection.getConnection();
            String sql = "UPDATE beneficiaries SET is_active = false WHERE beneficiary_id = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, beneficiaryId);
            
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean isBeneficiary(int userId, String accountNumber) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try {
            connection = DatabaseConnection.getConnection();
            String sql = "SELECT COUNT(*) as count FROM beneficiaries WHERE user_id = ? AND beneficiary_account_number = ? AND is_active = true";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            statement.setString(2, accountNumber);
            resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                return resultSet.getInt("count") > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    private Beneficiary mapResultSetToBeneficiary(ResultSet resultSet) throws SQLException {
        Beneficiary beneficiary = new Beneficiary();
        beneficiary.setBeneficiaryId(resultSet.getInt("beneficiary_id"));
        beneficiary.setUserId(resultSet.getInt("user_id"));
        beneficiary.setBeneficiaryAccountNumber(resultSet.getString("beneficiary_account_number"));
        beneficiary.setBeneficiaryName(resultSet.getString("beneficiary_name"));
        beneficiary.setNickname(resultSet.getString("nickname"));
        beneficiary.setActive(resultSet.getBoolean("is_active"));
        beneficiary.setAddedAt(resultSet.getTimestamp("added_at"));
        return beneficiary;
    }

}