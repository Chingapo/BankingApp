package com.techlabs.service;

import com.techlabs.dao.UserDAO;
import com.techlabs.model.User;
import java.util.List;

public class UserService {
    private UserDAO userDAO = new UserDAO();
    
    public User authenticate(String username, String password) {
        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            return null;
        }
        return userDAO.authenticate(username, password);
    }
    
    public boolean registerUser(User user) {
        if (user == null || user.getUsername() == null || user.getPassword() == null) {
            return false;
        }
        return userDAO.registerUser(user);
    }
    
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }
    
    public List<User> getPendingApprovals() {
        return userDAO.getPendingApprovals();
    }
    
    public boolean approveUser(int userId) {
        return userDAO.approveUser(userId);
    }
    
    public boolean deactivateUser(int userId) {
        return userDAO.deactivateUser(userId);
    }
    
    public boolean activateUser(int userId) {
        return userDAO.activateUser(userId);
    }
    
    public User getUserById(int userId) {
        return userDAO.getUserById(userId);
    }
}