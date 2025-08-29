<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Customer Dashboard</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"/>
  <style>
    body {
      background: #f4f7fb;
    }
    .nav-pills .nav-link {
      border-radius: 12px;
      font-weight: 500;
    }
    .nav-pills .nav-link.active {
      background: linear-gradient(45deg, #007bff, #00c6ff);
      color: white;
      box-shadow: 0 4px 10px rgba(0,0,0,0.15);
    }
    .card {
      border-radius: 16px;
      box-shadow: 0 4px 12px rgba(0,0,0,0.1);
    }
    h3 {
      font-weight: 600;
      color: #333;
    }
  </style>
</head>
<body>
<div class="container-fluid my-4">

  <!-- Header -->
  <div class="d-flex justify-content-between align-items-center mb-4">
    <h3>Welcome to Your Dashboard</h3>
    <a class="btn btn-outline-danger" href="${pageContext.request.contextPath}/logout">Logout</a>
  </div>

  <!-- Alerts -->
  <c:if test="${not empty param.msg}">
    <div class="alert alert-success alert-dismissible fade show" role="alert">
      ${param.msg}
      <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    </div>
  </c:if>
  <c:if test="${not empty param.error}">
    <div class="alert alert-danger alert-dismissible fade show" role="alert">
      ${param.error}
      <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    </div>
  </c:if>

  <!-- Layout with sidebar + content -->
  <div class="row">
    <!-- Sidebar -->
    <div class="col-md-3 mb-3">
      <div class="card p-3">
        <ul class="nav flex-column nav-pills" id="dashboardTabs" role="tablist">
          <li class="nav-item mb-2">
            <a class="nav-link active" id="accounts-tab" data-bs-toggle="pill" href="#accounts" role="tab">💳 View Accounts</a>
          </li>
          <li class="nav-item mb-2">
            <a class="nav-link" id="apply-tab" data-bs-toggle="pill" href="#apply" role="tab">➕ Apply Account</a>
          </li>
          <li class="nav-item mb-2">
            <a class="nav-link" id="beneficiaries-tab" data-bs-toggle="pill" href="#beneficiaries" role="tab">👥 Beneficiaries</a>
          </li>
          <li class="nav-item mb-2">
            <a class="nav-link" id="transfer-tab" data-bs-toggle="pill" href="#transfer" role="tab">💸 Transfer</a>
          </li>
          <li class="nav-item mb-2">
            <a class="nav-link" id="passbook-tab" data-bs-toggle="pill" href="#passbook" role="tab">📒 Passbook</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" id="profile-tab" data-bs-toggle="pill" href="#profile" role="tab">🙍 Profile</a>
          </li>
        </ul>
      </div>
    </div>

    <!-- Content -->
    <div class="col-md-9">
      <div class="card p-4 tab-content" id="dashboardTabsContent">

        <!-- Accounts -->
        <div class="tab-pane fade show active" id="accounts" role="tabpanel">
          <h5 class="mb-3">Your Accounts</h5>
          <div class="table-responsive">
            <table class="table table-hover align-middle">
              <thead class="table-light">
                <tr>
                  <th>Account No</th>
                  <th>Type</th>
                  <th>Balance</th>
                  <th>IFSC</th>
                  <th>Opened On</th>
                  <th>Status</th>
                </tr>
              </thead>
              <tbody>
                <c:choose>
                  <c:when test="${not empty accounts}">
                    <c:forEach var="acc" items="${accounts}">
                      <tr>
                        <td>${acc.accountNumber}</td>
                        <td>${acc.accountType}</td>
                        <td><fmt:formatNumber value="${acc.balance}" type="currency"/></td>
                        <td>${acc.ifscCode}</td>
                        <td>${acc.openedOn}</td>
                        <td>
                          <span class="badge 
                            <c:choose>
                              <c:when test='${acc.status eq "ACTIVE"}'>bg-success</c:when>
                              <c:when test='${acc.status eq "PENDING"}'>bg-warning text-dark</c:when>
                              <c:otherwise>bg-secondary</c:otherwise>
                            </c:choose>">
                            ${acc.status}
                          </span>
                        </td>
                      </tr>
                    </c:forEach>
                  </c:when>
                  <c:otherwise>
                    <tr>
                      <td colspan="6" class="text-center text-muted">No accounts found. Apply for an account.</td>
                    </tr>
                  </c:otherwise>
                </c:choose>
              </tbody>
            </table>
          </div>
        </div>

        <!-- Apply Account -->
        <div class="tab-pane fade" id="apply" role="tabpanel">
          <h5 class="mb-3">Open a New Account</h5>
          <a href="${pageContext.request.contextPath}/customer/apply_account.jsp" class="btn btn-primary">Apply Now</a>
        </div>

        <!-- Beneficiaries -->
        <div class="tab-pane fade" id="beneficiaries" role="tabpanel">
          <h5 class="mb-3">Manage Beneficiaries</h5>
          <c:forEach var="acc" items="${accounts}">
            <c:if test="${acc.status eq 'ACTIVE'}">
              <a class="btn btn-outline-secondary mb-2" href="${pageContext.request.contextPath}/ListBeneficiariesServlet?account_id=${acc.accountId}">
                Manage Beneficiaries for ${acc.accountNumber}
              </a><br/>
            </c:if>
          </c:forEach>
        </div>

        <!-- Transfer -->
        <div class="tab-pane fade" id="transfer" role="tabpanel">
          <h5 class="mb-3">Transfer Money</h5>
          <c:forEach var="acc" items="${accounts}">
            <c:if test="${acc.status eq 'ACTIVE'}">
              <a class="btn btn-outline-success mb-2" href="${pageContext.request.contextPath}/customer_transfer?from_account_id=${acc.accountId}">
                Transfer from ${acc.accountNumber}
              </a><br/>
            </c:if>
          </c:forEach>
        </div>

        <!-- Passbook -->
        <div class="tab-pane fade" id="passbook" role="tabpanel">
          <h5 class="mb-3">Your Passbooks</h5>
          <c:forEach var="acc" items="${accounts}">
            <a class="btn btn-outline-primary mb-2" href="${pageContext.request.contextPath}/customer_passbook?account_id=${acc.accountId}">
              View Passbook for ${acc.accountNumber}
            </a><br/>
          </c:forEach>
        </div>

        <!-- Profile -->
        <div class="tab-pane fade" id="profile" role="tabpanel">
          <h5 class="mb-3">Your Profile</h5>
          <a class="btn btn-outline-info" href="${pageContext.request.contextPath}/customer_profile">Go to Profile</a>
        </div>

      </div>
    </div>
  </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
