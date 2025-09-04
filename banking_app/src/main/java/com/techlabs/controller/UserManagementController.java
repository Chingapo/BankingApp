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

@WebServlet("/admin/users")
public class UserManagementController extends HttpServlet {
    private UserService userService = new UserService();
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        if (user == null || !user.isAdmin()) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        List<User> users = userService.getAllUsers();
        request.setAttribute("users", users);
        
        request.getRequestDispatcher("/admin/users.jsp").forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        if (user == null || !user.isAdmin()) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        String action = request.getParameter("action");
        int userId = Integer.parseInt(request.getParameter("userId"));
        
        if ("approve".equals(action)) {
            if (userService.approveUser(userId)) {
                request.setAttribute("success", "User approved successfully!");
            }
        } else if ("deactivate".equals(action)) {
            if (userService.deactivateUser(userId)) {
                request.setAttribute("success", "User deactivated successfully!");
            }
        } else if ("activate".equals(action)) {
            if (userService.activateUser(userId)) {
                request.setAttribute("success", "User activated successfully!");
            }
        }
        
        doGet(request, response);
    }
}