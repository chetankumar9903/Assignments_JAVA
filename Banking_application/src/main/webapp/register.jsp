<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Register</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">
  <style>
    body { 
      background: linear-gradient(135deg, #667eea, #764ba2);
      min-height:100vh; display:flex; align-items:center; justify-content:center;
      font-size: 0.9rem;
    }
    .card { 
      border-radius:12px; 
      box-shadow:0 6px 25px rgba(0,0,0,0.25);
      padding: 20px;
      max-width: 1000px;
      width: 100%;
    }
    .form-label { font-weight: 500; font-size: 0.85rem; margin-bottom: 2px;}
    .btn-custom { background:#667eea; color:white; border-radius:6px; font-size:0.9rem; }
    .btn-custom:hover { background:#5a67d8; color:white; }
    .password-toggle { cursor:pointer; font-size:0.8rem; }
    input, select { font-size: 0.9rem !important; }
    .password-match { font-size:0.8rem; margin-top:2px; }
  </style>
</head>
<body>
  <div class="container d-flex justify-content-center align-items-center">
    <div class="card">
      <h4 class="text-center mb-3">Create Account</h4>

      <form id="registerForm" action="register" method="post" novalidate>
        <div class="row g-2">

          <!-- Username -->
          <div class="col-md-4">
            <label class="form-label">Username</label>
            <input name="username" id="username" class="form-control form-control-sm" required>
          </div>

          <!-- Password -->
          <div class="col-md-4">
            <label class="form-label">Password</label>
            <input name="password" id="password" type="password" class="form-control form-control-sm" required minlength="6">
          </div>

          <!-- Confirm Password -->
          <div class="col-md-4">
            <label class="form-label">Confirm Password</label>
            <input id="confirmPassword" type="password" class="form-control form-control-sm" required>
            <small id="passwordMessage" class="password-match"></small>
            <div class="form-check mt-1">
              <input type="checkbox" class="form-check-input" id="showPassword">
              <label class="form-check-label password-toggle" for="showPassword">Show Password</label>
            </div>
          </div>

          <!-- First & Last Name -->
          <div class="col-md-4">
            <label class="form-label">First name</label>
            <input name="first_name" class="form-control form-control-sm" required>
          </div>
          <div class="col-md-4">
            <label class="form-label">Last name</label>
            <input name="last_name" class="form-control form-control-sm" required>
          </div>

          <!-- DOB / Phone / Email -->
          <div class="col-md-4">
            <label class="form-label">DOB</label>
            <input name="dob" id="dob" type="date" class="form-control form-control-sm" required>
          </div>
          <div class="col-md-4">
            <label class="form-label">Phone</label>
            <input name="phone" id="phone" class="form-control form-control-sm" required pattern="\\d{10}">
          </div>
          <div class="col-md-4">
            <label class="form-label">Email</label>
            <input name="email" id="email" type="email" class="form-control form-control-sm" required>
          </div>

          <!-- Address -->
          <div class="col-md-6">
            <label class="form-label">Address line 1</label>
            <input name="address1" class="form-control form-control-sm" required>
          </div>
          <div class="col-md-6">
            <label class="form-label">Address line 2</label>
            <input name="address2" class="form-control form-control-sm">
          </div>

          <!-- City / State / Postal -->
          <div class="col-md-4">
            <label class="form-label">City</label>
            <input name="city" class="form-control form-control-sm" required>
          </div>
          <div class="col-md-4">
            <label class="form-label">State</label>
            <input name="state" class="form-control form-control-sm" required>
          </div>
          <div class="col-md-4">
            <label class="form-label">Postal code</label>
            <input name="postal_code" class="form-control form-control-sm" required pattern="\\d{6}">
          </div>

          <!-- Govt ID -->
          <div class="col-md-6">
            <label class="form-label">Govt ID Type</label>
            <select name="govt_id_type" class="form-select form-select-sm" required>
              <option value="">Select</option>
              <option value="AADHAAR">AADHAAR</option>
              <option value="PAN">PAN</option>
              <option value="PASSPORT">PASSPORT</option>
              <option value="DL">DL</option>
            </select>
          </div>
          <div class="col-md-6">
            <label class="form-label">Govt ID Number</label>
            <input name="govt_id_value" class="form-control form-control-sm" required>
          </div>

          <!-- Submit -->
          <div class="col-12 d-grid mt-2">
            <button type="submit" class="btn btn-custom btn-sm py-2">Register</button>
          </div>
        </div>
      </form>

      <div class="mt-2 text-center small">
        Already have an account? <a href="login.jsp">Login</a>
      </div>

    </div>
  </div>

  <!-- Bootstrap Toast -->
  <div class="position-fixed bottom-0 end-0 p-3" style="z-index:11">
    <div id="toast" class="toast align-items-center text-bg-danger border-0" role="alert">
      <div class="d-flex">
        <div class="toast-body" id="toastMessage"></div>
        <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast"></button>
      </div>
    </div>
  </div>

  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
  <script>
    const toastEl = document.getElementById('toast');
    const toastBody = document.getElementById('toastMessage');
    const toast = new bootstrap.Toast(toastEl);

    function showToast(message) {
      toastBody.textContent = message;
      toast.show();
    }

    const form = document.getElementById("registerForm");
    const password = document.getElementById("password");
    const confirmPassword = document.getElementById("confirmPassword");
    const dob = document.getElementById("dob");
    const phone = document.getElementById("phone");
    const email = document.getElementById("email");
    const username = document.getElementById("username");
    const passwordMessage = document.getElementById("passwordMessage");

    // Live password match check
    confirmPassword.addEventListener("input", function() {
      if (confirmPassword.value.length > 0) {
        if (password.value === confirmPassword.value) {
          passwordMessage.textContent = "Passwords match ";
          passwordMessage.style.color = "green";
        } else {
          passwordMessage.textContent = "Passwords do not match ";
          passwordMessage.style.color = "red";
        }
      } else {
        passwordMessage.textContent = "";
      }
    });

    form.addEventListener("submit", function(e) {
      e.preventDefault(); // validation

      if (password.value !== confirmPassword.value) {
        showToast("Passwords do not match!");
        return;
      }

      const dobDate = new Date(dob.value);
      const today = new Date();
      const age = today.getFullYear() - dobDate.getFullYear();
      if (age < 18 || (age === 18 && today < new Date(dobDate.setFullYear(dobDate.getFullYear() + 18)))) {
        showToast("You must be at least 18 years old.");
        return;
      }

      if (!/^\d{10}$/.test(phone.value)) {
        showToast("Phone number must be 10 digits.");
        return;
      }

      if (!email.value.includes("@")) {
        showToast("Invalid email address.");
        return;
      }

      if (username.value.toLowerCase() === "admin") {
        showToast("Username already taken!");
        return;
      }

      form.submit();
    });

    document.getElementById("showPassword").addEventListener("change", function() {
      const type = this.checked ? "text" : "password";
      password.type = type;
      confirmPassword.type = type;
    });
  </script>
</body>
</html>
