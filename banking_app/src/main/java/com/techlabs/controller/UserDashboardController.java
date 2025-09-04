package com.techlabs.controller;

import com.techlabs.model.Account;
import com.techlabs.model.User;
import com.techlabs.service.AccountService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/user/dashboard")
public class UserDashboardController extends HttpServlet {
    private AccountService accountService = new AccountService();
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        if (user == null || user.isAdmin()) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        List<Account> accounts = accountService.getAccountsByUserId(user.getUserId());
        BigDecimal totalBalance = accountService.getTotalBalance(user.getUserId());
        
        request.setAttribute("accounts", accounts);
        request.setAttribute("totalBalance", totalBalance);
        
        request.getRequestDispatcher("/user/dashboard.jsp").forward(request, response);
    }
}