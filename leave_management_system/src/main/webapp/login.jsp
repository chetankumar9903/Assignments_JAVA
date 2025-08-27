<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Login | Leave Management</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

<style>
body {
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    background: linear-gradient(135deg, #6a11cb, #2575fc);
    height: 100vh;
    display: flex;
    flex-direction: column;
    align-items: center;
}

.container-box {
    background: rgba(255, 255, 255, 0.95);
    backdrop-filter: blur(6px);
    border-radius: 16px;
    box-shadow: 0 12px 30px rgba(0,0,0,0.2);
    padding: 1.6rem 2.1rem;
    width: 100%;
    max-width: 420px;
    margin-top: 40px;
}

.container-box h2 {
    text-align: center;
    margin-bottom: 1.5rem;
    color: #333;
    font-weight: 600;
}

.form-control {
    border-radius: 10px;
    padding: 0.7rem;
    font-size: 1rem;
    border: 1px solid #ddd;
    transition: 0.3s ease;
}
.form-control:focus {
    border-color: #667eea;
    box-shadow: 0 0 6px rgba(102,126,234,0.4);
}

.btn-custom {
    width: 100%;
    padding: 0.75rem;
    border-radius: 10px;
    font-size: 1rem;
    font-weight: 600;
    background: linear-gradient(135deg, #667eea, #5563c1);
    border: none;
    color: white;
    transition: all 0.3s ease;
}
.btn-custom:hover {
    background: linear-gradient(135deg, #5563c1, #444b9a);
    transform: scale(1.02);
}

a {
    text-decoration: none;
    color: #667eea;
    font-weight: 500;
}
a:hover {
    text-decoration: underline;
}

.small-text {
    font-size: 0.85rem;
    color: #555;
    cursor: pointer;
}

#alert-box {
    position: fixed;
    top: 20px;
    right: -400px;
    min-width: 280px;
    z-index: 9999;
    transition: right 0.5s ease;
    box-shadow: 0px 4px 10px rgba(0,0,0,0.2);
    border-radius: 10px;
}
</style>
</head>
<body>

<jsp:include page="navbar.jsp"/>

<!-- Success Message for registration -->
<c:if test="${param.msg == 'registered'}">
    <div id="alert-box" class="alert alert-success">
        Registration successful! Please login.
    </div>
</c:if>
        
<!-- Success Message for logout -->
<c:if test="${param.msg == 'logout'}">
    <div id="alert-box" class="alert alert-success">
        Logout Successful. Thank you!
    </div>
</c:if> 

<!-- Error Message for login -->
<c:if test="${not empty sessionScope.errorMsg}">
    <div id="alert-box" class="alert alert-danger">
         ${sessionScope.errorMsg}
    </div>
    <c:remove var="errorMsg" scope="session"/>
</c:if>

<script>
    const alertBox = document.getElementById("alert-box");
    if (alertBox) {
        setTimeout(() => { alertBox.style.right = "20px"; }, 200);
        setTimeout(() => { alertBox.style.right = "-400px"; }, 5000);
    }
</script>

<div class="container-box">
    <h2>🔑 Login</h2>

    <form action="login" method="post">
        <div class="mb-3">
            <label class="form-label">Username</label>
            <input type="text" name="username" class="form-control" required>
        </div>

        <div class="mb-3">
            <label class="form-label">Password</label> 
            <input type="password" id="password" name="password" class="form-control" required>
            <div class="form-check mt-2">
                <input type="checkbox" class="form-check-input" id="showPassword">
                <label class="form-check-label small-text" for="showPassword">Show Password</label>
            </div>
        </div>

        <div class="mb-3">
            <label class="form-label">Login as</label>
            <select name="role" class="form-control" required>
                <option value="EMPLOYEE">Employee</option>
                <option value="ADMIN">Admin</option>
            </select>
        </div>

        <button type="submit" class="btn btn-custom">Login</button>

        <div class="text-center mt-3">
            <small>Don't have an account? <a href="register.jsp">Register</a></small>
        </div>
    </form>
</div>

<script>
    document.getElementById("showPassword").addEventListener("change", function () {
        const pwd = document.getElementById("password");
        pwd.type = this.checked ? "text" : "password";
    });
</script>

</body>
</html>
