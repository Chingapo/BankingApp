package com.techlabs.controller;

import com.techlabs.model.Account;
import com.techlabs.model.Beneficiary;
import com.techlabs.model.User;
import com.techlabs.service.AccountService;
import com.techlabs.service.BeneficiaryService;
import com.techlabs.service.TransactionService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/user/transfer")
public class TransferController extends HttpServlet {
    private AccountService accountService = new AccountService();
    private BeneficiaryService beneficiaryService = new BeneficiaryService();
    private TransactionService transactionService = new TransactionService();
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        if (user == null || user.isAdmin()) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        List<Account> accounts = accountService.getAccountsByUserId(user.getUserId());
        List<Beneficiary> beneficiaries = beneficiaryService.getBeneficiariesByUserId(user.getUserId());
        
        request.setAttribute("accounts", accounts);
        request.setAttribute("beneficiaries", beneficiaries);
        
        request.getRequestDispatcher("/user/transfer.jsp").forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        if (user == null || user.isAdmin()) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        try {
            int fromAccountId = Integer.parseInt(request.getParameter("fromAccount"));
            String toAccountNumber = request.getParameter("toAccount");
            BigDecimal amount = new BigDecimal(request.getParameter("amount"));
            String description = request.getParameter("description");
            
            boolean success = transactionService.transferMoney(
                fromAccountId, toAccountNumber, amount, description, user.getUserId()
            );
            
            if (success) {
                request.setAttribute("success", "Transfer completed successfully!");
            } else {
                request.setAttribute("error", "Transfer failed. Please check your balance and beneficiary.");
            }
            
        } catch (Exception e) {
            request.setAttribute("error", "An error occurred during transfer.");
            e.printStackTrace();
        }
        
        doGet(request, response);
    }
}