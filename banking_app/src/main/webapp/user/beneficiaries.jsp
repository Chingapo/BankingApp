<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.techlabs.model.User" %>
<%@ page import="com.techlabs.model.Beneficiary" %>
<%@ page import="java.util.List" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null || user.isAdmin()) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }
    
    List<Beneficiary> beneficiaries = (List<Beneficiary>) request.getAttribute("beneficiaries");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Beneficiaries - Banking System</title>
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
                        <a class="nav-link" href="${pageContext.request.contextPath}/user/transfer">Transfer</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active" href="${pageContext.request.contextPath}/user/beneficiaries">Beneficiaries</a>
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
        <h2>Manage Beneficiaries</h2>
        
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
            <div class="col-md-4">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">Add New Beneficiary</h5>
                        <form action="${pageContext.request.contextPath}/user/beneficiaries" method="post">
                            <input type="hidden" name="action" value="add">
                            <div class="mb-3">
                                <label for="accountNumber" class="form-label">Account Number</label>
                                <input type="text" class="form-control" id="accountNumber" name="accountNumber" required>
                            </div>
                            <div class="mb-3">
                                <label for="beneficiaryName" class="form-label">Beneficiary Name</label>
                                <input type="text" class="form-control" id="beneficiaryName" name="beneficiaryName" required>
                            </div>
                            <div class="mb-3">
                                <label for="nickname" class="form-label">Nickname (Optional)</label>
                                <input type="text" class="form-control" id="nickname" name="nickname">
                            </div>
                            <button type="submit" class="btn btn-primary">Add Beneficiary</button>
                        </form>
                    </div>
                </div>
            </div>
            
            <div class="col-md-8">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">Your Beneficiaries</h5>
                        <div class="table-responsive">
                            <table class="table table-striped">
                                <thead>
                                    <tr>
                                        <th>Name</th>
                                        <th>Account Number</th>
                                        <th>Nickname</th>
                                        <th>Action</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <% if (beneficiaries != null && !beneficiaries.isEmpty()) {
                                        for (Beneficiary beneficiary : beneficiaries) { %>
                                    <tr>
                                        <td><%= beneficiary.getBeneficiaryName() %></td>
                                        <td><%= beneficiary.getBeneficiaryAccountNumber() %></td>
                                        <td><%= beneficiary.getNickname() != null ? beneficiary.getNickname() : "-" %></td>
                                        <td>
                                            <form action="${pageContext.request.contextPath}/user/beneficiaries" method="post" style="display:inline;">
                                                <input type="hidden" name="action" value="remove">
                                                <input type="hidden" name="beneficiaryId" value="<%= beneficiary.getBeneficiaryId() %>">
                                                <button type="submit" class="btn btn-danger btn-sm">Remove</button>
                                            </form>
                                        </td>
                                    </tr>
                                    <% }
                                    } else { %>
                                    <tr>
                                        <td colspan="4" class="text-center">No beneficiaries found</td>
                                    </tr>
                                    <% } %>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>