<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Login</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
  <style>
    body { 
      background: linear-gradient(90deg,#667eea,#764ba2); 
      min-height:100vh; 
      display:flex; 
      align-items:center; 
    }
    .card { 
      border-radius:12px; 
      box-shadow:0 8px 30px rgba(0,0,0,0.25); 
    }
    .btn-custom { 
      background:#667eea; 
      color:white; 
      border-radius:8px; 
    }
  </style>
</head>
<body>
  <div class="container">
    <div class="row justify-content-center mt-5">
      <div class="col-md-5">
        <div class="card p-4">
          <h4 class="text-center mb-3">Banking System - Login</h4>
          
          <c:if test="${not empty param.msg}">
            <div class="alert alert-success">${param.msg}</div>
          </c:if>
          <c:if test="${not empty param.error}">
            <div class="alert alert-danger">${param.error}</div>
          </c:if>

          <form action="login" method="post"> <!-- doGet requirement -->
            <div class="mb-2">
              <label class="form-label">Username</label>
              <input name="username" class="form-control" required />
            </div>
            <div class="mb-2">
              <label class="form-label">Password</label>
              <input id="password" name="password" type="password" class="form-control" required />
              <div class="form-check mt-1">
                <input type="checkbox" class="form-check-input" id="showPassword" onclick="togglePassword()">
                <label for="showPassword" class="form-check-label">Show Password</label>
              </div>
            </div>
            <div class="mb-3">
              <label class="form-label">Login as</label>
              <select name="role" class="form-select">
                <option value="CUSTOMER">Customer</option>
                <option value="ADMIN">Admin</option>
              </select>
            </div>

            <div class="d-grid">
              <button class="btn btn-custom">Login</button>
            </div>
          </form>

          <div class="text-center mt-3">
            <small>Don't have an account? <a href="register.jsp">Register</a></small>
          </div>
        </div>
      </div>
    </div>
  </div>

  <script>
    function togglePassword() {
      const passwordField = document.getElementById("password");
      passwordField.type = passwordField.type === "password" ? "text" : "password";
    }
  </script>
</body>
</html>
