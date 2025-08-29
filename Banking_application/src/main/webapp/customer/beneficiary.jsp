<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<title>Manage Beneficiaries</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
<style>
  body {
    background: #f4f7fb;
  }
  .card {
    border-radius: 16px;
    box-shadow: 0 4px 12px rgba(0,0,0,0.1);
  }
  h2, h3 {
    font-weight: 600;
    color: #0d6efd;
  }
  .btn {
    border-radius: 10px;
  }
  .form-label {
    font-weight: 500;
  }
</style>
</head>
<body class="container my-5">

  <!-- Title -->
  <h2 class="mb-4">👥 Manage Your Beneficiaries</h2>

  <!-- Alerts -->
  <c:if test="${param.msg=='added'}">
      <div class="alert alert-success">✅ Beneficiary Added!</div>
  </c:if>
  <c:if test="${param.msg=='failed'}">
      <div class="alert alert-danger">❌ Failed to add beneficiary. Please check details.</div>
  </c:if>
  <c:if test="${param.msg=='deleted'}">
      <div class="alert alert-info">ℹ️ Beneficiary Deleted.</div>
  </c:if>

  <!-- Beneficiaries List -->
  <div class="card p-4 mb-4">
    <h5 class="mb-3">Your Saved Beneficiaries</h5>
    <div class="table-responsive">
      <table class="table table-hover align-middle">
        <thead class="table-light">
          <tr>
            <th>ID</th>
            <th>Nickname</th>
            <th>Name</th>
            <th>Account Info</th>
            <th>Type</th>
            <th>Action</th>
          </tr>
        </thead>
        <tbody>
        <c:forEach var="b" items="${beneficiaries}">
          <tr>
            <td>${b.beneficiaryId}</td>
            <td>${b.nickname}</td>
            <td>${b.beneficiaryName}</td>
            <td>
              <c:choose>
                <c:when test="${b.internal}">
                  Internal Account ID: ${b.beneficiaryAccountId}
                </c:when>
                <c:otherwise>
                  ${b.beneficiaryAccountNumber} <br/>
                  <small class="text-muted">IFSC: ${b.beneficiaryIfscCode}</small>
                </c:otherwise>
              </c:choose>
            </td>
            <td><c:out value="${b.internal ? 'Internal' : 'External'}" /></td>
            <td>
              <a class="btn btn-sm btn-danger" 
                 href="DeleteBeneficiaryServlet?id=${b.beneficiaryId}&account_id=${param.account_id}">
                 🗑 Delete
              </a>
            </td>
          </tr>
        </c:forEach>
        </tbody>
      </table>
    </div>
  </div>

  <!-- Add Beneficiary Form -->
  <div class="card p-4 mb-3">
    <h3 class="mb-3">➕ Add New Beneficiary</h3>
    <form action="AddBeneficiaryServlet" method="post" class="row g-3">
      <input type="hidden" name="account_id" value="${param.account_id}">

      <div class="col-md-4">
        <label class="form-label">Nickname (optional)</label>
        <input type="text" name="nickname" class="form-control" placeholder="Nickname">
      </div>

      <div class="col-md-4">
        <label class="form-label">Beneficiary Name</label>
        <input type="text" name="beneficiaryName" class="form-control" placeholder="Beneficiary Name" required>
      </div>

      <div class="col-md-4">
        <label class="form-label">Type</label>
        <select name="type" class="form-select" onchange="toggleFields(this.value)" required>
          <option value="internal">Internal</option>
          <option value="external">External</option>
        </select>
      </div>

      <!-- Internal Fields -->
      <div id="internalFields" class="col-md-6">
        <label class="form-label">Internal Account ID</label>
        <input type="number" name="internalAccountId" class="form-control" placeholder="Internal Account ID" required>
      </div>

      <!-- External Fields -->
      <div id="externalFields" class="col-md-6" style="display:none;">
        <label class="form-label">Account Number</label>
        <input type="text" name="accountNumber" class="form-control mb-2" placeholder="Account Number">
        <label class="form-label">IFSC Code</label>
        <input type="text" name="ifsc" class="form-control" placeholder="IFSC Code">
      </div>

      <div class="col-12">
        <button type="submit" class="btn btn-primary px-4">Save Beneficiary</button>
      </div>
    </form>
  </div>

  <!-- Back Link -->
             <div class="text-center mt-3">
                <a class="btn btn-outline-secondary" href="${pageContext.request.contextPath}/customer_dashboard">⬅ Back to Dashboard</a>
            </div>


<script>
function toggleFields(val){
    let internal = document.getElementById("internalFields");
    let external = document.getElementById("externalFields");

    if(val === "internal"){
        internal.style.display = "block";
        external.style.display = "none";
        internal.querySelector("input").setAttribute("required", "required");
        external.querySelectorAll("input").forEach(el => el.removeAttribute("required"));
    } else {
        internal.style.display = "none";
        external.style.display = "block";
        internal.querySelector("input").removeAttribute("required");
        external.querySelectorAll("input").forEach(el => el.setAttribute("required", "required"));
    }
}
</script>

</body>
</html>
