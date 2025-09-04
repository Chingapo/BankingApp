package com.techlabs.controller;

import com.techlabs.model.Beneficiary;
import com.techlabs.model.User;
import com.techlabs.service.BeneficiaryService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/user/beneficiaries")
public class BeneficiaryController extends HttpServlet {
    private BeneficiaryService beneficiaryService = new BeneficiaryService();
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        if (user == null || user.isAdmin()) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        List<Beneficiary> beneficiaries = beneficiaryService.getBeneficiariesByUserId(user.getUserId());
        request.setAttribute("beneficiaries", beneficiaries);
        
        request.getRequestDispatcher("/user/beneficiaries.jsp").forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        if (user == null || user.isAdmin()) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        String action = request.getParameter("action");
        
        if ("add".equals(action)) {
            String accountNumber = request.getParameter("accountNumber");
            String beneficiaryName = request.getParameter("beneficiaryName");
            String nickname = request.getParameter("nickname");
            
            Beneficiary beneficiary = new Beneficiary();
            beneficiary.setUserId(user.getUserId());
            beneficiary.setBeneficiaryAccountNumber(accountNumber);
            beneficiary.setBeneficiaryName(beneficiaryName);
            beneficiary.setNickname(nickname);
            
            if (beneficiaryService.addBeneficiary(beneficiary)) {
                request.setAttribute("success", "Beneficiary added successfully!");
            } else {
                request.setAttribute("error", "Failed to add beneficiary.");
            }
        } else if ("remove".equals(action)) {
            int beneficiaryId = Integer.parseInt(request.getParameter("beneficiaryId"));
            
            if (beneficiaryService.removeBeneficiary(beneficiaryId)) {
                request.setAttribute("success", "Beneficiary removed successfully!");
            } else {
                request.setAttribute("error", "Failed to remove beneficiary.");
            }
        }
        
        doGet(request, response);
    }
}