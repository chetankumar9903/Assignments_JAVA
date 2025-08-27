<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Register | Leave Management</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(135deg, #6a11cb, #2575fc);
            min-height: 100vh;
            display: flex;
            flex-direction: column;
        }
        .register-card {
            background: #fff;
            border-radius: 15px;
            box-shadow: 0 6px 20px rgba(0,0,0,0.15);
            padding: 30px;
            width: 800px;
            margin: auto;
        }
        .form-label { font-weight: 500; }
        .btn-custom {
            background: #2575fc;
            color: #fff;
            border-radius: 8px;
        }
        .btn-custom:hover { background: #1a5ed8; }
        #alert-box {
            position: fixed;
            top: 20px;
            right: -400px;
            min-width: 280px;
            z-index: 9999;
            transition: right 0.5s ease;
            box-shadow: 0px 4px 10px rgba(0,0,0,0.2);
        }
        .error-msg {
            color: red;
            font-size: 0.85rem;
            margin-top: 5px;
            display: none;
        }
    </style>
</head>
<body>

<!-- Navbar -->
<jsp:include page="navbar.jsp" />

<!-- Error Message -->
<c:if test="${param.error == 'exists'}">
    <div id="alert-box" class="alert alert-danger">Username already exists. Try another.</div>
</c:if>

<div class="register-card">
    <h3 class="text-center mb-4">Create Your Account</h3>
    <form id="registerForm" action="register" method="post" >
        <div class="row g-3">
            <!-- Left Column -->
            <div class="col-md-6">
                <label class="form-label">Full Name</label>
                <input type="text" name="full_name" class="form-control" required>
            </div>

            <div class="col-md-6">
                <label class="form-label">Username</label>
                <input type="text" name="username" class="form-control" required>
            </div>

            <div class="col-md-6">
                <label class="form-label">Password</label>
                <input type="password" id="password" name="password" class="form-control" required>
                <div class="form-check mt-1">
                    <input type="checkbox" class="form-check-input" id="showPassword">
                    <label class="form-check-label" for="showPassword">Show Password</label>
                </div>
            </div>

            <div class="col-md-6">
                <label class="form-label">Confirm Password</label>
                <input type="password" id="confirmPassword" name="confirm_password" class="form-control" required>
                <div id="passwordError" class="error-msg">Passwords do not match</div>
            </div>

            <div class="col-md-6">
                <label class="form-label">Department</label>
                <input type="text" name="dept" class="form-control" required>
            </div>

            <div class="col-md-6">
                <label class="form-label">Register as</label>
                <select name="role" class="form-select" required>
                    <option value="EMPLOYEE">Employee</option>
                </select>
            </div>
        </div>

        <div class="text-center mt-4">
            <button type="submit" class="btn btn-custom px-4">Register</button>
        </div>

        <div class="text-center mt-3">
            <small>Already have an account? <a href="login.jsp">Login</a></small>
        </div>
    </form>
</div>

<script>
    // Show/Hide password
    document.getElementById("showPassword").addEventListener("change", function() {
        const password = document.getElementById("password");
        const confirmPassword = document.getElementById("confirmPassword");
        if (this.checked) {
            password.type = "text";
            confirmPassword.type = "text";
        } else {
            password.type = "password";
            confirmPassword.type = "password";
        }
    });

    // Inline validation for password match
    const form = document.getElementById("registerForm");
    const password = document.getElementById("password");
    const confirmPassword = document.getElementById("confirmPassword");
    const passwordError = document.getElementById("passwordError");

    function validatePassword() {
        if (password.value && confirmPassword.value && password.value !== confirmPassword.value) {
            passwordError.style.display = "block";
            confirmPassword.classList.add("is-invalid");
            return false;
        } else {
            passwordError.style.display = "none";
            confirmPassword.classList.remove("is-invalid");
            return true;
        }
    }

    confirmPassword.addEventListener("input", validatePassword);
    password.addEventListener("input", validatePassword);

    form.addEventListener("submit", function(event) {
        if (!validatePassword()) {
            event.preventDefault();
        }
    });

    // Auto-hide alert
    const alertBox = document.getElementById("alert-box");
    if (alertBox) {
        setTimeout(() => { alertBox.style.right = "20px"; }, 100);
        setTimeout(() => { alertBox.style.right = "-400px"; }, 5000);
    }
</script>

</body>
</html>
