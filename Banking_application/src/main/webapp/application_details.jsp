<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Application Details</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"/>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet"/>
    <style>
        body { background: #f0f2f5; font-family: 'Segoe UI', sans-serif; }
        .card { border-radius: 15px; box-shadow: 0 10px 25px rgba(0,0,0,0.1); }
        h5 { font-weight: 600; }
        dt { font-weight: 600; }
        dd { margin-bottom: 1rem; }
        .btn-approve { background: linear-gradient(90deg,#28a745,#198754); color: white; }
        .btn-approve:hover { background: linear-gradient(90deg,#198754,#28a745); }
        .btn-reject { background: linear-gradient(90deg,#dc3545,#b02a37); color: white; }
        .btn-reject:hover { background: linear-gradient(90deg,#b02a37,#dc3545); }
        .status-badge { font-size: 0.9rem; padding: 0.35em 0.75em; border-radius: 0.35rem; font-weight: 500; }
        .status-pending { background-color: #ffc107; color: #212529; }
        .status-approved { background-color: #198754; color: white; }
        .status-rejected { background-color: #dc3545; color: white; }
        .section-title { font-size: 1.2rem; margin-bottom: 15px; border-bottom: 2px solid #dee2e6; padding-bottom: 5px; }
        .info-row { display: flex; justify-content: space-between; padding: 0.4rem 0; border-bottom: 1px dashed #dee2e6; }
        .info-label { font-weight: 600; color: #495057; }
        .info-value { color: #212529; }
        .card-header { background: #0d6efd; color: white; font-weight: 600; border-top-left-radius: 15px; border-top-right-radius: 15px; }
        .card-body { padding: 2rem; }
    </style>
</head>
<body>
<div class="container my-5">
    <div class="card">
        <div class="card-header">Application Details</div>
        <div class="card-body">
            <c:if test="${not empty appDetails}">
                <div class="section mb-4">
                    <div class="section-title">Application Information</div>
                    <div class="info-row"><div class="info-label">Application ID</div><div class="info-value">${appDetails.application_id}</div></div>
                    <div class="info-row"><div class="info-label">Account Type</div><div class="info-value">${appDetails.account_type}</div></div>
                    <div class="info-row"><div class="info-label">Initial Deposit</div><div class="info-value">${appDetails.initial_deposit}</div></div>
                    <div class="info-row">
                        <div class="info-label">Status</div>
                        <div class="info-value">
                            <span class="status-badge
                                <c:choose>
                                    <c:when test='${appDetails.status == "PENDING"}'>status-pending</c:when>
                                    <c:when test='${appDetails.status == "APPROVED"}'>status-approved</c:when>
                                    <c:when test='${appDetails.status == "REJECTED"}'>status-rejected</c:when>
                                </c:choose>
                            ">
                                <i class="fa-solid fa-circle me-1"></i>${appDetails.status}
                            </span>
                        </div>
                    </div>
                    <div class="info-row"><div class="info-label">Applied On</div><div class="info-value">${appDetails.created_at}</div></div>
                    <div class="info-row"><div class="info-label">Remarks</div><div class="info-value">${appDetails.remarks != null ? appDetails.remarks : '-'}</div></div>
                </div>

                <div class="section mb-4">
                    <div class="section-title">Customer Information</div>
                    <div class="info-row"><div class="info-label">Full Name</div><div class="info-value">${appDetails.first_name} ${appDetails.last_name}</div></div>
                    <div class="info-row"><div class="info-label">Date of Birth</div><div class="info-value">${appDetails.dob}</div></div>
                    <div class="info-row"><div class="info-label">Address</div>
                        <div class="info-value">
                            ${appDetails.address_line1}, ${appDetails.address_line2}, ${appDetails.city}, ${appDetails.state} - ${appDetails.postal_code}
                        </div>
                    </div>
                    <div class="info-row"><div class="info-label">Email</div><div class="info-value">${appDetails.email}</div></div>
                    <div class="info-row"><div class="info-label">Phone</div><div class="info-value">${appDetails.phone}</div></div>
                    <div class="info-row"><div class="info-label">Government ID</div><div class="info-value">${appDetails.govt_id_type} - ${appDetails.govt_id_value}</div></div>
                    <div class="info-row">
                        <div class="info-label">KYC Status</div>
                        <div class="info-value">
                            <span class="status-badge
                                <c:choose>
                                    <c:when test='${appDetails.kyc_status == "VERIFIED"}'>status-approved</c:when>
                                    <c:when test='${appDetails.kyc_status == "REJECTED"}'>status-rejected</c:when>
                                    <c:otherwise>status-pending</c:otherwise>
                                </c:choose>
                            ">
                                <i class="fa-solid fa-circle me-1"></i>${appDetails.kyc_status}
                            </span>
                        </div>
                    </div>
                </div>

                <div class="text-end">
                    <button class="btn btn-approve me-2" data-bs-toggle="modal" data-bs-target="#approveModal"><i class="fa-solid fa-check me-1"></i>Approve</button>
                    <button class="btn btn-reject me-2" data-bs-toggle="modal" data-bs-target="#rejectModal"><i class="fa-solid fa-xmark me-1"></i>Reject</button>
                    <a href="${pageContext.request.contextPath}/admin_dashboard?tab=pending" class="btn btn-secondary"><i class="fa-solid fa-arrow-left me-1"></i>Back</a>
                </div>
            </c:if>

            <c:if test="${empty appDetails}">
                <div class="alert alert-warning">Application details not found.</div>
                <a href="${pageContext.request.contextPath}/admin_dashboard?tab=pending" class="btn btn-secondary">Back</a>
            </c:if>
        </div>
    </div>
</div>

<!-- Approve & Reject Modals (same as previous version) -->

<!-- Approve Modal -->
<div class="modal fade" id="approveModal" tabindex="-1" aria-labelledby="approveModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered">
    <div class="modal-content">
      <form method="get" action="${pageContext.request.contextPath}/ApplicationDetailsServlet">
        <div class="modal-header bg-success text-white">
          <h5 class="modal-title" id="approveModalLabel"><i class="fa-solid fa-check me-2"></i>Approve Application</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
        </div>
        <div class="modal-body">
            <input type="hidden" name="application_id" value="${appDetails.application_id}" />
            <input type="hidden" name="action" value="APPROVE" />
            <p>Are you sure you want to approve this application?</p>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
          <button type="submit" class="btn btn-approve">Approve</button>
        </div>
      </form>
    </div>
  </div>
</div>

<!-- Reject Modal -->
<div class="modal fade" id="rejectModal" tabindex="-1" aria-labelledby="rejectModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered">
    <div class="modal-content">
      <form method="get" action="${pageContext.request.contextPath}/ApplicationDetailsServlet">
        <div class="modal-header bg-danger text-white">
          <h5 class="modal-title" id="rejectModalLabel"><i class="fa-solid fa-xmark me-2"></i>Reject Application</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
        </div>
        <div class="modal-body">
            <input type="hidden" name="application_id" value="${appDetails.application_id}" />
            <input type="hidden" name="action" value="REJECT" />
            <div class="mb-3">
                <label for="remarks" class="form-label">Remarks (optional)</label>
                <textarea class="form-control" id="remarks" name="remarks" rows="3" placeholder="Enter reason for rejection..."></textarea>
            </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
          <button type="submit" class="btn btn-reject">Reject</button>
        </div>
      </form>
    </div>
  </div>
</div>

</body>
</html>
