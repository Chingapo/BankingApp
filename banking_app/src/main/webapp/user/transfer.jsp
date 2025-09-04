<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.techlabs.model.User" %>
<%@ page import="com.techlabs.model.Account" %>
<%@ page import="com.techlabs.model.Beneficiary" %>
<%@ page import="java.util.List" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null || user.isAdmin()) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }
    
    List<Account> accounts = (List<Account>) request.getAttribute("accounts");
    List<Beneficiary> beneficiaries = (List<Beneficiary>) request.getAttribute("beneficiaries");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Transfer Money - Banking System</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
        <div class="container-fluid">
            <a class="navbar-brand" href="#">Banking System</a>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav me-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/user/dashboard">Dashboard</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active" href="${pageContext.request.contextPath}/user/transfer">Transfer</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/user/beneficiaries">Beneficiaries</a>
                    </li>
                </ul>
                <span class="navbar-text text-white me-3">
                    Welcome, <%= user.getFullName() %>
                </span>
                <a href="${pageContext.request.contextPath}/logout" class="btn btn-outline-light btn-sm">Logout</a>
            </div>
        </div>
    </nav>
    
    <div class="container mt-4">
        <h2>Transfer Money</h2>
        
        <% if (request.getAttribute("error") != null) { %>
        <div class="alert alert-danger" role="alert">
            <%= request.getAttribute("error") %>
        </div>
        <% } %>
        
        <% if (request.getAttribute("success") != null) { %>
        <div class="alert alert-success" role="alert">
            <%= request.getAttribute("success") %>
        </div>
        <% } %>
        
        <div class="row mt-4">
            <div class="col-md-6">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">Make a Transfer</h5>
                        
                        <form action="${pageContext.request.contextPath}/user/transfer" method="post">
                            <div class="mb-3">
                                <label for="fromAccount" class="form-label">From Account</label>
                                <select class="form-select" id="fromAccount" name="fromAccount" required>
                                    <option value="">Select Account</option>
                                    <% if (accounts != null) {
                                        for (Account account : accounts) { %>
                                    <option value="<%= account.getAccountId() %>">
                                        <%= account.getAccountNumber() %> - <%= account.getAccountType() %> (Balance: ₹<%= account.getBalance() %>)
                                    </option>
                                    <% }
                                    } %>
                                </select>
                            </div>
                            
                            <div class="mb-3">
                                <label for="toAccount" class="form-label">To Account (Beneficiary)</label>
                                <select class="form-select" id="toAccount" name="toAccount" required>
                                    <option value="">Select Beneficiary</option>
                                    <% if (beneficiaries != null) {
                                        for (Beneficiary beneficiary : beneficiaries) { %>
                                    <option value="<%= beneficiary.getBeneficiaryAccountNumber() %>">
                                        <%= beneficiary.getBeneficiaryName() %> - <%= beneficiary.getBeneficiaryAccountNumber() %>
                                    </option>
                                    <% }
                                    } %>
                                </select>
                            </div>
                            
                            <div class="mb-3">
                                <label for="amount" class="form-label">Amount</label>
                                <input type="number" class="form-control" id="amount" name="amount" 
                                       step="0.01" min="0.01" required>
                            </div>
                            
                            <div class="mb-3">
                                <label for="description" class="form-label">Description</label>
                                <textarea class="form-control" id="description" name="description" rows="2"></textarea>
                            </div>
                            
                            <button type="submit" class="btn btn-primary">Transfer</button>
                            <a href="${pageContext.request.contextPath}/user/dashboard" class="btn btn-secondary">Cancel</a>
                        </form>
                    </div>
                </div>
            </div>
            
            <div class="col-md-6">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">Disclaimer</h5>
                        <ul>
                            <li>You can only transfer money to registered beneficiaries who have an account with us.</li>
                        </ul>
                        
                        <% if (beneficiaries == null || beneficiaries.isEmpty()) { %>
                        <div class="alert alert-warning mt-3">
                            You have no beneficiaries added. 
                            <a href="${pageContext.request.contextPath}/user/beneficiaries">Add a beneficiary</a> first.
                        </div>
                        <% } %>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>