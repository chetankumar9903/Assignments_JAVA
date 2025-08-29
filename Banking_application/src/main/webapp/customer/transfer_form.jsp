<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Money Transfer - Banking App</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-6">

            <div class="card shadow-lg border-0 rounded-3">
                <div class="card-header bg-success text-white text-center py-3">
                    <h4 class="mb-0">💸 Money Transfer</h4>
                </div>

                <div class="card-body p-4">

                    <!-- Error Message -->
                    <c:if test="${not empty errorMsg}">
                        <div class="alert alert-danger text-center">
                            ${errorMsg}
                        </div>
                    </c:if>

                    <!-- Transfer Form -->
                    <form action="customer_transfer" method="post" class="needs-validation" novalidate>

                        <!-- Beneficiary -->
                        <div class="mb-3">
                            <label class="form-label fw-semibold">Select Beneficiary <span class="text-danger">*</span></label>
                            <select name="beneficiaryId" class="form-select" required>
                                <option value="">-- Select a Beneficiary --</option>
                                <c:forEach var="b" items="${beneficiaries}">
                                    <option value="${b.beneficiaryId}">
                                        ${b.beneficiaryName} - ${b.beneficiaryAccountNumber}
                                    </option>
                                </c:forEach>
                            </select>
                            <div class="invalid-feedback">Please select a beneficiary.</div>
                        </div>

                        <!-- Amount -->
                        <div class="mb-3">
                            <label class="form-label fw-semibold">Amount <span class="text-danger">*</span></label>
                            <input type="number" step="0.01" min="1" name="amount" class="form-control" placeholder="Enter amount" required>
                            <div class="invalid-feedback">Please enter a valid amount.</div>
                        </div>

                        <!-- Description -->
                        <div class="mb-3">
                            <label class="form-label fw-semibold">Description</label>
                            <input type="text" name="description" class="form-control" placeholder="Optional note (e.g. rent, bills)">
                        </div>

                        <!-- Submit Button -->
                        <button type="submit" class="btn btn-success w-100 py-2 fw-bold">Transfer Money</button>
                    </form>

                </div>
            </div>

            <!-- Back Button -->
            <div class="text-center mt-3">
                <a class="btn btn-outline-secondary" href="${pageContext.request.contextPath}/customer_dashboard">⬅ Back to Dashboard</a>
            </div>

        </div>
    </div>
</div>

<script>
    // Bootstrap form validation
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

</body>
</html>
