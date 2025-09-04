package com.techlabs.controller;

import com.techlabs.model.User;
import com.techlabs.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/dashboard")
public class AdminDashboardController extends HttpServlet {
    private UserService userService = new UserService();
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        if (user == null || !user.isAdmin()) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        List<User> allUsers = userService.getAllUsers();
        List<User> pendingUsers = userService.getPendingApprovals();
        
        int totalUsers = allUsers.size();
        int pendingApprovals = pendingUsers.size();
        int activeUsers = 0;
        for (User u : allUsers) {
            if (u.isActive()) {
                activeUsers++;
            }
        }
        
        request.setAttribute("totalUsers", totalUsers);
        request.setAttribute("pendingApprovals", pendingApprovals);
        request.setAttribute("activeUsers", activeUsers);
        
        request.getRequestDispatcher("/admin/dashboard.jsp").forward(request, response);
    }
}