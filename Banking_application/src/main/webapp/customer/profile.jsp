<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Customer Profile</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"/>
  <script>
    // Toggle password section visibility
    function togglePasswordSection() {
        const section = document.getElementById("password-section");
        section.classList.toggle("d-none");
    }
  </script>
</head>
<body class="bg-light">

<div class="container my-5">
  <div class="card shadow-lg rounded-3">
    <div class="card-header bg-primary text-white d-flex justify-content-between align-items-center">
      <h4 class="mb-0">My Profile </h4>
      <a href="customer_dashboard" class="btn btn-light btn-sm">Back</a>
    </div>
    <div class="card-body">

      <!-- Alerts (${profile.userId} -->
      <c:if test="${not empty param.msg}">
        <div class="alert alert-success">${param.msg}</div>
      </c:if>
      <c:if test="${not empty param.error}">
        <div class="alert alert-danger">${param.error}</div>
      </c:if>
      <c:if test="${not empty requestScope.successMsg}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
          ${requestScope.successMsg}
          <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
      </c:if>
      <c:if test="${not empty requestScope.errorMsg}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
          ${requestScope.errorMsg}
          <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
      </c:if>
      <!-- Alerts End -->

      <!-- Profile Update Form -->
      <form method="post" action="customer_profile" class="row g-3 needs-validation" novalidate>
        <input type="hidden" name="update" value="1">

        <div class="col-md-6">
          <label class="form-label fw-bold">First Name</label>
          <input type="text" name="first_name" value="${profile.firstName}" class="form-control" required/>
          <div class="invalid-feedback">First name is required.</div>
        </div>
        <div class="col-md-6">
          <label class="form-label fw-bold">Last Name</label>
          <input type="text" name="last_name" value="${profile.lastName}" class="form-control" required/>
          <div class="invalid-feedback">Last name is required.</div>
        </div>

        <div class="col-md-6">
          <label class="form-label fw-bold">Email</label>
          <input type="email" name="email" value="${profile.email}" class="form-control" required/>
          <div class="invalid-feedback">Valid email is required.</div>
        </div>
        <div class="col-md-6">
          <label class="form-label fw-bold">Phone</label>
          <input type="text" name="phone" value="${profile.phone}" class="form-control" required pattern="^[0-9]{10}$"/>
          <div class="invalid-feedback">Enter a valid 10-digit phone number.</div>
        </div>

        <div class="col-md-6">
          <label class="form-label fw-bold">DOB</label>
          <input type="date" name="dob" value="${profile.dob}" class="form-control" required/>
          <div class="invalid-feedback">Date of Birth is required.</div>
        </div>

        <div class="col-md-12">
          <label class="form-label fw-bold">Address Line 1</label>
          <input type="text" name="address1" value="${profile.addressLine1}" class="form-control" required/>
          <div class="invalid-feedback">Address Line 1 is required.</div>
        </div>
        <div class="col-md-12">
          <label class="form-label fw-bold">Address Line 2</label>
          <input type="text" name="address2" value="${profile.addressLine2}" class="form-control"/>
        </div>

        <div class="col-md-4">
          <label class="form-label fw-bold">City</label>
          <input type="text" name="city" value="${profile.city}" class="form-control" required/>
          <div class="invalid-feedback">City is required.</div>
        </div>
        <div class="col-md-4">
          <label class="form-label fw-bold">State</label>
          <input type="text" name="state" value="${profile.state}" class="form-control" required/>
          <div class="invalid-feedback">State is required.</div>
        </div>
        <div class="col-md-4">
          <label class="form-label fw-bold">Postal Code</label>
          <input type="text" name="postal" value="${profile.postalCode}" class="form-control" required pattern="^[0-9]{6}$"/>
          <div class="invalid-feedback">Enter a valid 6-digit postal code.</div>
        </div>

        <!-- Readonly Info -->
        <div class="col-md-6">
          <label class="form-label fw-bold">Govt ID</label>
          <input type="text" value="${profile.govtIdType} - ${profile.govtIdValue}" readonly class="form-control bg-light"/>
        </div>
        <div class="col-md-6">
          <label class="form-label fw-bold">KYC Status</label>
          <input type="text" value="${profile.kycStatus}" readonly class="form-control bg-light"/>
        </div>

        <div class="col-12 text-end">
          <button type="submit" class="btn btn-primary px-4">Update</button>
        </div>
      </form>

      <!-- Change Password Toggle -->
      <div class="mt-4">
        <button type="button" class="btn btn-outline-primary" onclick="togglePasswordSection()">Change Password</button>
      </div>

      <!-- Password Section (hidden by default) -->
      <div id="password-section" class="d-none mt-3">
        <c:choose>
          <c:when test="${profile.kycStatus ne 'VERIFIED'}">
            <div class="alert alert-warning">
              You cannot change password until your KYC is approved.
            </div>
          </c:when>
          <c:otherwise>
            <div class="card border-0 shadow-sm">
              <div class="card-header bg-success text-white">
                Change Password
              </div>
              <div class="card-body">
                <form action="changepassword" method="post" class="needs-validation" novalidate>
                  <div class="mb-3">
                    <label for="oldPassword" class="form-label fw-bold">Old Password</label>
                    <input type="password" name="oldPassword" id="oldPassword" class="form-control" required>
                    <div class="invalid-feedback">Please enter your old password.</div>
                  </div>
                  <div class="mb-3">
                    <label for="newPassword" class="form-label fw-bold">New Password</label>
                    <input type="password" name="newPassword" id="newPassword" class="form-control" required minlength="6">
                    <div class="invalid-feedback">New password must be at least 6 characters.</div>
                  </div>
                  <div class="mb-3">
                    <label for="confirmPassword" class="form-label fw-bold">Confirm New Password</label>
                    <input type="password" name="confirmPassword" id="confirmPassword" class="form-control" required>
                    <div class="invalid-feedback">Please confirm your new password.</div>
                  </div>
                  <button type="submit" class="btn btn-success">Update Password</button>
                </form>

                <!-- Success / Error Messages -->
                <c:if test="${not empty requestScope.successMsg}">
                  <div class="alert alert-success mt-3" id="password-success">${requestScope.successMsg}</div>
                  <script>
                    document.addEventListener("DOMContentLoaded", function() {
                      document.getElementById("password-section").classList.add("d-none");
                    });
                  </script>
                </c:if>
                <c:if test="${not empty requestScope.errorMsg}">
                  <div class="alert alert-danger mt-3">${requestScope.errorMsg}</div>
                </c:if>
              </div>
            </div>
          </c:otherwise>
        </c:choose>
      </div>
    </div>
  </div>
</div>

<script>
  // Bootstrap validation script
  (function () {
    'use strict'
    const forms = document.querySelectorAll('.needs-validation')
    Array.from(forms).forEach(function (form) {
      form.addEventListener('submit', function (event) {
        if (!form.checkValidity()) {
          event.preventDefault()
          event.stopPropagation()
        }
        form.classList.add('was-validated')
      }, false)
    })
  })()
</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
