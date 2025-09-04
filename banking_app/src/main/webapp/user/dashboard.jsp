<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.techlabs.model.User" %>
<%@ page import="com.techlabs.model.Account" %>
<%@ page import="java.util.List" %>
<%@ page import="java.math.BigDecimal" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null || user.isAdmin()) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }
    
    List<Account> accounts = (List<Account>) request.getAttribute("accounts");
    BigDecimal totalBalance = (BigDecimal) request.getAttribute("totalBalance");
    Account currentAccount = accounts != null && !accounts.isEmpty() ? accounts.get(0) : null;
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Dashboard - Banking System</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
        <div class="container-fluid">
            <a class="navbar-brand" href="#">Banking System</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav me-auto">
                    <li class="nav-item">
                        <a class="nav-link active" href="${pageContext.request.contextPath}/user/dashboard">Dashboard</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/user/transfer">Transfer</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/user/beneficiaries">Beneficiaries</a>
                    </li>
                </ul>
                <div class="navbar-text text-white me-3">
                    <% if (currentAccount != null) { %>
                    Current A/C: <strong><%= currentAccount.getAccountNumber() %></strong>
                    <% } %>
                </div>
                <span class="navbar-text text-white me-3">
                    Welcome, <%= user.getFullName() %>
                </span>
                <a href="${pageContext.request.contextPath}/logout" class="btn btn-outline-light btn-sm">Logout</a>
            </div>
        </div>
    </nav>
    
    <div class="container mt-4">
        <h2>Dashboard</h2>
        
        <div class="row mt-4">
            <div class="col-md-4">
                <div class="card text-white bg-primary mb-3">
                    <div class="card-header">Total Balance</div>
                    <div class="card-body">
                        <h5 class="card-title">₹ <%= totalBalance != null ? totalBalance : "0.00" %></h5>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card text-white bg-success mb-3">
                    <div class="card-header">Active Accounts</div>
                    <div class="card-body">
                        <h5 class="card-title"><%= accounts != null ? accounts.size() : 0 %></h5>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card text-white bg-info mb-3">
                    <div class="card-header">Quick Actions</div>
                    <div class="card-body">
                        <a href="${pageContext.request.contextPath}/user/transfer" class="btn btn-light btn-sm">Transfer Money</a>
                    </div>
                </div>
            </div>
        </div>
        
        <div class="row mt-4">
            <div class="col-md-12">
                <h4>Your Accounts</h4>
                <div class="table-responsive">
                    <table class="table table-striped">
                        <thead>
                            <tr>
                                <th>Account Number</th>
                                <th>Account Type</th>
                                <th>Balance</th>
                                <th>Status</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% if (accounts != null && !accounts.isEmpty()) {
                                for (Account account : accounts) { %>
                            <tr>
                                <td><%= account.getAccountNumber() %></td>
                                <td><%= account.getAccountType() %></td>
                                <td>₹ <%= account.getBalance() %></td>
                                <td>
                                    <span class="badge bg-success">Active</span>
                                </td>
                            </tr>
                            <% }
                            } else { %>
                            <tr>
                                <td colspan="4" class="text-center">No accounts found</td>
                            </tr>
                            <% } %>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>