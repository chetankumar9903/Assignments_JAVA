<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin Dashboard</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet" />
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
	
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">


<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css" rel="stylesheet">
	
<style>

    .btn:hover {
        transform: translateY(-2px);
    }
    .nav-tabs .nav-link {
        transition: all 0.2s ease-in-out;
    }
    .nav-tabs .nav-link:hover {
        background-color: #0d6efd;
        color: #fff !important;
        font-weight: 600;
        border-radius: 5px;
    }

</style>
<script>
    let selectedUserId = null;

    // --- Toast Function (moved to top) ---
    function showToast(message, isSuccess=true) {
        const toastEl = document.getElementById('actionToast');
        const toastMessage = document.getElementById('toastMessage');

        if (!toastEl || !toastMessage) {
            console.warn('Toast elements not found');
            return;
        }

        toastMessage.textContent = message;
        toastEl.className = 'toast align-items-center text-white ' +
                            (isSuccess ? 'bg-success' : 'bg-danger') +
                            ' border-0';

        const toast = new bootstrap.Toast(toastEl, { delay: 3000 });
        toast.show();
    }

  

    // --- Pending Applications (existing JS) ---
    function confirmApprove(id) {
        if (confirm('Approve application #' + id + ' ?')) {
            window.location.href = '${pageContext.request.contextPath}/admin/approve?application_id=' + encodeURIComponent(id);
        }
    }

    function promptRejectApplication(id) {
        let remarks = prompt('Enter reason for rejection (optional):');
        if (remarks !== null) {
            window.location.href = '${pageContext.request.contextPath}/admin/reject?application_id=' + encodeURIComponent(id) + '&remarks=' + encodeURIComponent(remarks);
        }
    }

    // Open Details Modal
    function openDetailsModal(userId, username, email, phone, fullName, dob, address, kycStatus, createdAt, govtIdType, govtIdValue, remarks) {
        selectedUserId = userId;

        document.getElementById('detailUserId').textContent = userId;
        document.getElementById('detailUsername').textContent = username;
        document.getElementById('detailEmail').textContent = email;
        document.getElementById('detailPhone').textContent = phone;
        document.getElementById('detailFullName').textContent = fullName || '-';
        document.getElementById('detailDob').textContent = dob || '-';
        document.getElementById('detailAddress').textContent = address || '-';
        document.getElementById('detailCreated').textContent = createdAt || '-';
        document.getElementById('detailGovtId').textContent = (govtIdType && govtIdValue) ? (govtIdType + " - " + govtIdValue) : '-';
        document.getElementById('detailRemarks').textContent = remarks || '-';

        const kycStatusSpan = document.getElementById("detailKyc");
        kycStatusSpan.textContent = kycStatus;
        kycStatusSpan.className = "badge";
        if (kycStatus === "VERIFIED") {
          kycStatusSpan.classList.add("bg-success");
        } else if (kycStatus === "REJECTED") {
          kycStatusSpan.classList.add("bg-danger");
        } else {
          kycStatusSpan.classList.add("bg-warning","text-dark");
        }

        new bootstrap.Modal(document.getElementById('detailsModal')).show();
    }

    // DOM Content Loaded Event Handler
    document.addEventListener("DOMContentLoaded", function() {
        // Handle URL parameter messages
        const urlParams = new URLSearchParams(window.location.search);
        const msg = urlParams.get('msg');
        const error = urlParams.get('error');
        const type = urlParams.get('type');

        if (msg) {
            const isSuccess = type !== 'danger';
            showToast(msg, isSuccess);
        }
        if (error) {
            showToast(error, false);
        }

        // Event Listeners for Modal Actions
        const openVerifyModalBtn = document.getElementById("openVerifyModal");
        if (openVerifyModalBtn) {
            openVerifyModalBtn.addEventListener("click", function () {
                new bootstrap.Modal(document.getElementById("verifyModal")).show();
            });
        }

        const openRejectModalBtn = document.getElementById("openRejectModal");
        if (openRejectModalBtn) {
            openRejectModalBtn.addEventListener("click", function () {
                document.getElementById("rejectUserId").value = selectedUserId;
                new bootstrap.Modal(document.getElementById("rejectModal")).show();
            });
        }

        // Confirm Verify AJAX
        const confirmVerifyBtn = document.getElementById("confirmVerifyBtn");
        if (confirmVerifyBtn) {
            confirmVerifyBtn.addEventListener("click", function () {
                if (!selectedUserId) {
                    showToast("No user selected", false);
                    return;
                }
                
                fetch("${pageContext.request.contextPath}/admin_dashboard", {
                    method: "POST",
                    headers: { "Content-Type": "application/x-www-form-urlencoded" },
                    body: "activate_user_id=" + encodeURIComponent(selectedUserId)
                })
                .then(response => response.text())
                .then(() => {
                    // Hide the verify modal
                    const verifyModal = bootstrap.Modal.getInstance(document.getElementById("verifyModal"));
                    if (verifyModal) verifyModal.hide();
                    
                    // Hide the details modal if it's open
                    const detailsModal = bootstrap.Modal.getInstance(document.getElementById("detailsModal"));
                    if (detailsModal) detailsModal.hide();
                    
                    // ✅ REDIRECT WITH MESSAGE PARAMS!
                    window.location.href = 
                        '${pageContext.request.contextPath}/admin_dashboard?tab=inactive'
                        + '&msg=' + encodeURIComponent('Customer verified & activated successfully!')
                        + '&type=success';
                
                    
                    //showToast("Customer verified & activated successfully!", true);
                    // Reload page after showing toast
                    //setTimeout(() => location.reload(), 1500);
                })
                .catch(err => {
                    console.error('Error verifying user:', err);
                    showToast("Error verifying customer: " + err, false);
                });
            });
        }

        // Confirm Reject AJAX
        const confirmRejectBtn = document.getElementById("confirmRejectBtn");
        if (confirmRejectBtn) {
            confirmRejectBtn.addEventListener("click", function () {
                const remarks = document.getElementById("rejectRemarks").value.trim();
                if (!remarks) {
                    alert("Remarks are required to reject a customer.");
                    return;
                }

                if (!selectedUserId) {
                    showToast("No user selected", false);
                    return;
                }

                fetch("${pageContext.request.contextPath}/admin_dashboard", {
                    method: "POST",
                    headers: { "Content-Type": "application/x-www-form-urlencoded" },
                    body: "reject_user_id=" + encodeURIComponent(selectedUserId) + "&remarks=" + encodeURIComponent(remarks)
                })
                .then(response => response.text())
                .then(() => {
                    // Hide the reject modal
                    const rejectModal = bootstrap.Modal.getInstance(document.getElementById("rejectModal"));
                    if (rejectModal) rejectModal.hide();
                    
                    // Hide the details modal if it's open
                    const detailsModal = bootstrap.Modal.getInstance(document.getElementById("detailsModal"));
                    if (detailsModal) detailsModal.hide();
                 // ✅ REDIRECT WITH MESSAGE PARAMS!
                    window.location.href =
                        '${pageContext.request.contextPath}/admin_dashboard?tab=inactive'
                        + '&msg=' + encodeURIComponent('Customer rejected successfully!')
                        + '&type=danger';
               
                    
                    //showToast("Customer rejected successfully!", false);
                    // Reload page after showing toast
                   // setTimeout(() => location.reload(), 1500);
                })
                .catch(err => {
                    console.error('Error rejecting user:', err);
                    showToast("Error rejecting customer: " + err, false);
                });
            });
        }
    });
    </script>
</head>
<body>
	<div class="container my-4" style="background: linear-gradient(135deg, #f5f7fa, #c3cfe2); border-radius: 12px; padding: 30px;">
    <!-- Header -->
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2 class="text-primary fw-bold d-flex align-items-center">
            <i class="bi bi-speedometer2 me-2"></i> Admin Dashboard
        </h2>
        <a href="${pageContext.request.contextPath}/logout" 
           class="btn btn-danger fw-bold d-flex align-items-center shadow-sm" 
           style="transition: transform 0.2s;">
           <i class="bi bi-box-arrow-right me-1"></i> Logout
        </a>
    </div>

    <!-- Tabs -->
    <ul class="nav nav-tabs nav-fill mb-4 shadow-sm rounded" style="background: #ffffff;">
        <li class="nav-item">
            <a class="nav-link ${activeTab=='inactive' ? 'active fw-bold text-white bg-primary' : 'text-primary'}"
               href="?tab=inactive">
               <i class="bi bi-person-x me-1"></i> Inactive Customers
            </a>
        </li>
        <li class="nav-item">
            <a class="nav-link ${activeTab=='pending' ? 'active fw-bold text-white bg-primary' : 'text-primary'}"
               href="?tab=pending">
               <i class="bi bi-hourglass-split me-1"></i> Pending Applications
            </a>
        </li>
        <li class="nav-item">
            <a class="nav-link ${activeTab=='application' ? 'active fw-bold text-white bg-primary' : 'text-primary'}"
               href="?tab=application">
               <i class="bi bi-people me-1"></i> Customer Details
            </a>
        </li>
        <li class="nav-item">
            <a class="nav-link ${activeTab=='transactions' ? 'active fw-bold text-white bg-primary' : 'text-primary'}"
               href="?tab=transactions">
               <i class="bi bi-currency-exchange me-1"></i> Transactions
            </a>
        </li>
    </ul>

    <!-- Content Card  card shadow-lg p-4 rounded-->
    <div class="" style="transition: transform 0.2s;">
        <c:choose>
            <c:when test="${activeTab=='inactive'}">
                <jsp:include page="fragments/inactive-customers.jsp" />
            </c:when>
            <c:when test="${activeTab=='pending'}">
                <jsp:include page="fragments/pending-applications.jsp" />
            </c:when>
            <c:when test="${activeTab=='application'}">
                <jsp:include page="fragments/all-customers.jsp" />
            </c:when>
            <c:when test="${activeTab=='transactions'}">
                <jsp:include page="fragments/admin-transactions.jsp" />
            </c:when>
            <c:otherwise>
                <div class="text-center py-4 text-muted">
                    Select a tab to view content
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>

	<!-- Modals -->
	<jsp:include page="fragments/modals.jsp" />


	<script>
let selectedAppId = null;

function openAppDetailsModal(appId) {
    selectedAppId = appId;

    fetch('${pageContext.request.contextPath}/ApplicationDetailsServlet?application_id=' + encodeURIComponent(appId))
    .then(res => res.json())
    .then(data => {
        if (!data) {
            alert('Application details not found');
            return;
        }
        // Populate modal fields
        document.getElementById('appDetailId').textContent = data.application_id;
        document.getElementById('appDetailUsername').textContent = data.username;
        document.getElementById('appDetailFullName').textContent = data.full_name || '-';
        document.getElementById('appDetailEmail').textContent = data.email;
        document.getElementById('appDetailPhone').textContent = data.phone;
        document.getElementById('appDetailAccountType').textContent = data.account_type;
        document.getElementById('appDetailDeposit').textContent = data.initial_deposit;
        document.getElementById('appDetailAppliedOn').textContent = data.created_at;
        document.getElementById('appDetailRemarks').textContent = data.remarks || '-';

        // Show modal
        new bootstrap.Modal(document.getElementById('appDetailsModal')).show();
    })
    .catch(err => console.error('Error fetching application details:', err));
}

// Approve button
document.getElementById('appApproveBtn').addEventListener('click', function() {
    if (!selectedAppId) return;
    if (!confirm('Approve application #' + selectedAppId + '?')) return;

    window.location.href = '${pageContext.request.contextPath}/ApplicationDetailsServlet?application_id=' 
        + encodeURIComponent(selectedAppId) + '&action=APPROVE';
});

// Reject button
document.getElementById('appRejectBtn').addEventListener('click', function() {
    if (!selectedAppId) return;
    let remarks = prompt('Enter reason for rejection (optional):');
    if (remarks === null) return;

    window.location.href = '${pageContext.request.contextPath}/ApplicationDetailsServlet?application_id=' 
        + encodeURIComponent(selectedAppId) + '&action=REJECT&remarks=' + encodeURIComponent(remarks);
});



// approved custoemrs
function openApprovedDetailsModal(cust) {
    // User Info
    document.getElementById('approvedUserId').textContent = cust.userId;
    document.getElementById('approvedUsername').textContent = cust.username;
    document.getElementById('approvedEmail').textContent = cust.email;
    document.getElementById('approvedPhone').textContent = cust.phone;

    // Customer Info
    document.getElementById('approvedFullName').textContent = cust.firstName + ' ' + cust.lastName;
    document.getElementById('approvedDob').textContent = cust.dob;
    document.getElementById('approvedAddress').textContent = cust.address;
    document.getElementById('approvedGovtIdType').textContent = cust.govtIdType;
    document.getElementById('approvedGovtIdValue').textContent = cust.govtIdValue;
    document.getElementById('approvedKyc').textContent = cust.kyc;

    // Account Info
    const acc = cust.account || {};
    document.getElementById('approvedAccNo').textContent = acc.accountNumber || '';
    document.getElementById('approvedAccType').textContent = acc.accountType || '';
    document.getElementById('approvedBalance').textContent = acc.balance || '';
    document.getElementById('approvedAccStatus').textContent = acc.accountStatus || '';
    document.getElementById('approvedIfsc').textContent = acc.ifsc || '';
    document.getElementById('approvedBankName').textContent = acc.bankName || '';
    document.getElementById('approvedBranchName').textContent = acc.branchName || '';
    document.getElementById('approvedBranchCity').textContent = acc.branchCity || '';

    // Application Info
    document.getElementById('approvedAppId').textContent = cust.applicationId;
    document.getElementById('approvedInitDeposit').textContent = cust.initialDeposit;
    document.getElementById('approvedAppCreated').textContent = cust.appCreated;
    document.getElementById('approvedAppStatus').textContent = cust.status;
    document.getElementById('approvedReviewedBy').textContent = cust.reviewedBy;
    document.getElementById('approvedReviewedByName').textContent = cust.reviewedByName;
    document.getElementById('approvedReviewedAt').textContent = cust.reviewedAt;
    document.getElementById('approvedRemarks').textContent = cust.remarks || '-';

    new bootstrap.Modal(document.getElementById('approvedDetailsModal')).show();
}





/** ----------------------all above clear  **/

	
</script>



</body>
</html>