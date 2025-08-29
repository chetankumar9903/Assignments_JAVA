<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Apply for Account</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"/>
  <style>
    body {
      background: linear-gradient(135deg, #e3f2fd, #f1f8ff);
      min-height: 100vh;
      display: flex;
      align-items: center;
      justify-content: center;
    }
    .card {
      border-radius: 16px;
      box-shadow: 0 6px 18px rgba(0,0,0,0.1);
    }
    h4 {
      font-weight: 600;
      color: #0d6efd;
    }
    .form-label {
      font-weight: 500;
    }
    .btn-primary {
      border-radius: 10px;
      font-weight: 500;
      padding: 10px;
    }
    .btn-link {
      text-decoration: none;
      font-weight: 500;
    }
  </style>
</head>
<body>
<div class="container">
  <div class="card p-4 col-md-6 mx-auto">
    <h4 class="mb-3 text-center">Apply for New Account</h4>
    <p class="text-muted text-center">Fill in the details to open a new account</p>

    <c:if test="${not empty param.error}">
      <div class="alert alert-danger text-center">${param.error}</div>
    </c:if>

    <form action="${pageContext.request.contextPath}/customer/applyAccount" method="post">
      <div class="mb-3">
        <label class="form-label">Account Type</label>
        <select name="account_type" class="form-select" required>
          <option value="SAVINGS">Savings Account</option>
          <option value="CURRENT">Current Account</option>
        </select>
      </div>

      <div class="mb-3">
        <label class="form-label">Initial Deposit (optional)</label>
        <input type="number" step="0.01" name="initial_deposit" class="form-control" placeholder="Enter amount"/>
      </div>

      <div class="d-grid">
        <button class="btn btn-primary"> Submit Application</button>
      </div>
    </form>

    <div class="mt-4 text-center">
      <a href="${pageContext.request.contextPath}/customer_dashboard" class="btn btn-link">← Back to Dashboard</a>
    </div>
  </div>
</div>
</body>
</html>
