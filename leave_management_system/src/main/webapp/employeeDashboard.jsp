
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page session="true"%>

<!DOCTYPE html>
<html>
<head>
<title>Employee Dashboard</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
	<link
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css"
	rel="stylesheet">

<style>
body {
	background: linear-gradient(to right, #f8f9fa, #e9ecef);
	font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}

.employee-details {
	margin-top: 1px;
	border-radius: 12px;
	box-shadow: 0 4px 10px rgba(13, 110, 253, 0.15);
	background-color: #ffffff;
	padding: 20px;
	text-align: center;
}

.employee-details h5 {
	font-weight: 700;
	margin-bottom: 20px;
	color: #0d6efd;
}

.employee-details .row>div {
	font-size: 1.1rem;
	margin-bottom: 10px;
}

.employee-details strong {
	font-weight: 600;
	color: #1a1a1a;
}

.card {
	margin-top: 20px;
	border-radius: 12px;
	box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
	text-align: center;
}

form .row>div {
	margin-bottom: 15px;
}

.btn-custom {
	background: #0d6efd;
	color: white;
	border-radius: 8px;
	padding: 10px 25px;
	font-weight: 600;
	box-shadow: 0 3px 6px rgba(13, 110, 253, 0.4);
	float: right;
	transition: background-color 0.3s ease, box-shadow 0.3s ease;
}

.btn-custom:hover {
	background-color: #084298;
	box-shadow: 0 5px 15px rgba(8, 66, 152, 0.6);
}

.table-hover tbody tr:hover {
	background-color: #e9f0fe;
	/** cursor: pointer; **/
}

.table td, .table th {
	padding: 12px 15px;
	vertical-align: middle;
}

.badge {
	font-size: 0.9rem;
	padding: 5px 12px;
}

#success-alert {
	position: fixed;
	top: 20px;
	right: -400px;
	background-color: #4CAF50;
	color: white;
	padding: 15px 30px;
	border-radius: 8px;
	box-shadow: 0 4px 6px rgba(0, 0, 0, 0.2);
	transition: right 0.5s ease-in-out;
	z-index: 9999;
}

#alert-box {
	position: fixed;
	top: 20px;
	right: -600px;
	padding: 15px 30px;
	border-radius: 8px;
	box-shadow: 0 4px 6px rgba(0, 0, 0, 0.2);
	transition: right 0.5s ease-in-out;
	z-index: 9999;
}
</style>
</head>
<body>
	<jsp:include page="navbar.jsp" />


	<c:if test="${not empty sessionScope.successMsg}">
		<div id="success-alert" class="alert alert-success">${sessionScope.successMsg}</div>
		<c:remove var="successMsg" scope="session" />
	</c:if>
	<c:if test="${param.msg == 'success'}">
		<div id="success-alert" class="alert alert-success">Leave
			applied successfully!</div>
	</c:if>

	<c:choose>
		<c:when test="${not empty error}">
			<div id="alert-box" class="alert alert-danger">${error}</div>
		</c:when>
		<c:when test="${param.msg == 'error'}">
			<div id="alert-box" class="alert alert-danger">Something went
				wrong. Please try again.</div>
		</c:when>
	</c:choose>

	<script>
	const alertBox = document.getElementById("success-alert");
	if(alertBox){ setTimeout(() => { alertBox.style.right = "20px"; }, 100); setTimeout(() => { alertBox.style.right = "-400px"; }, 5000);}
	const alertBox1 = document.getElementById("alert-box");
	if(alertBox1){ setTimeout(() => { alertBox1.style.right = "20px"; }, 100); setTimeout(() => { alertBox1.style.right = "-600px"; }, 5000);}
	</script>
	
	<div class="container py-4">
	
	
<div class="text-center mt-7">
	<h2 class="fw-bold text-success">
		<i class="bi bi-person-badge me-2"></i> Employee Dashboard
	</h2>
	<p class="text-muted">View your leave balance and track applications easily</p>
</div>
	
	</div>


	<div class="container employee-details">
		<h5>Employee Details</h5>
		<div class="row">
			<div class="col-md-4">
				<strong>Name:</strong> ${user.fullname}
			</div>
			<div class="col-md-4">
				<strong>Employee ID:</strong> ${user.id}
			</div>
			<div class="col-md-4">
				<strong>Department:</strong> ${user.dept}
			</div>
		</div>
	</div>


	<div class="container mt-4">
		<h4 class="mb-3 text-center fw-bold">Leave Balance (Yearly -
			Used/Total)</h4>

		<div class="d-flex justify-content-center flex-wrap gap-4">
			<c:forEach var="s" items="${leaveSummary}" varStatus="loop">
				<div class="card shadow-sm text-white px-4 py-3 rounded-3"
					style="min-width: 180px; 
	                        background: <c:choose>
	                                      <c:when test='${loop.index % 3 == 0}'>linear-gradient(135deg, #4facfe, #00f2fe)</c:when>
	                                      <c:when test='${loop.index % 3 == 1}'>linear-gradient(135deg, #43e97b, #38f9d7)</c:when>
	                                      <c:otherwise>linear-gradient(135deg, #f85032, #e73827)</c:otherwise>
	                                   </c:choose>;">
					<h6 class="fw-bold mb-1">${s.typeName}</h6>
					<span class="fs-5 fw-semibold">${s.used} / ${s.allowed}</span>
				</div>
			</c:forEach>
		</div>
	</div>


	<div class="container mt-5">
		<div class="card p-4 shadow-lg border-0 rounded-4">
			<h4 class="mb-3 fw-bold text-primary text-center">Apply for
				Leave</h4>
			<form action="LeaveController" method="post" id="leaveForm">
				<div class="row g-4">
					<div class="col-md-3">
						<label class="form-label fw-semibold">From Date</label> <input
							type="date" id="dateFrom" name="date_from"
							class="form-control rounded-3 shadow-sm"
							value="<c:out value='${pendingLeave.dateFrom}' default='${param.date_from}'/>"
							required>
					</div>
					<div class="col-md-3">
						<label class="form-label fw-semibold">To Date</label> <input
							type="date" id="dateTo" name="date_to"
							class="form-control rounded-3 shadow-sm"
							value="<c:out value='${pendingLeave.dateTo}' default='${param.date_to}'/>"
							required>
					</div>
					<div class="col-md-3">
						<label class="form-label fw-semibold">Leave Type</label> <select
							name="leave_type" class="form-select rounded-3 shadow-sm"
							required>
							<option value="">Select Type</option>
							<c:forEach var="type" items="${leaveTypes}">
								<option value="${type.typeId}"
									<c:if test="${selectedTypeId == type.typeId || param.leave_type == type.typeId}">selected</c:if>>
									${type.typeName}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-md-3">
						<label class="form-label fw-semibold">Reason</label> <input
							type="text" name="reason"
							class="form-control rounded-3 shadow-sm"
							value="<c:out value='${pendingLeave.reason}' default='${param.reason}'/>"
							required>
					</div>
				</div>
				<div class="text-end mt-4">
					<button type="submit"
						class="btn btn-lg px-5 btn-primary rounded-pill shadow">Apply</button>
				</div>
			</form>
		</div>
	</div>


	<div class="container mt-5 mb-5">
		<div class="card p-4 shadow-lg border-0 rounded-4">
			<h4 class="mb-3 fw-bold text-dark text-center">My Leave
				Applications</h4>
			<c:if test="${empty leaves}">
				<div class="alert alert-info text-center">You haven’t applied
					for any leaves yet.</div>
			</c:if>
			<c:if test="${not empty leaves}">
				<table
					class="table table-striped table-hover align-middle rounded-3 overflow-hidden">
					<thead class="table-primary">
						<tr>
							<th>#</th>
							<th>From</th>
							<th>To</th>
							<th>No. of Days</th>
							<th>Type</th>
							<th>Reason</th>
							<th>Status</th>
							<th>Applied On</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="leave" items="${leaves}" varStatus="loop">
							<tr>
								<td>${loop.index + 1}</td>
								<td>${leave.dateFromFormatted}</td>
								<td>${leave.dateToFormatted}</td>
								<td>${leave.noOfDays}</td>

								<!--   <td>${leave.leaveType}</td> -->
								<td>${leave.leaveType} 
								<!-- 
								<c:if
										test="${not empty leave.overrideReason}">
										<br>
										<span class="badge bg-danger mt-1 small"> ⚠ Exceeded
											monthly limit </span>
									</c:if>
								 -->
								
								</td>
								
								<!-- <td>${leave.reason}</td> -->
								
								<td>
                        <c:if test="${not empty leave.overrideReason}">
                            <span class="badge bg-danger"
                                  style="cursor:pointer;"
                                  data-bs-toggle="modal"
                                  data-bs-target="#overrideModal"
                                  data-reason="${leave.overrideReason}">
                                Exceeds Limit
                            </span>
                        </c:if>
                        <div class="mt-1">
                            <span >${leave.reason}</span>
                        </div>
                    </td>

								<td><c:choose>
										<c:when test="${leave.status == 'Pending'}">
											<span class="badge bg-warning text-dark px-3 py-2">Pending</span>
										</c:when>
										<c:when test="${leave.status == 'Approved'}">
											<span class="badge bg-success px-3 py-2">Approved</span>
										</c:when>
										<c:otherwise>
											<span class="badge bg-danger px-3 py-2">Rejected</span>
										</c:otherwise>
									</c:choose></td>
								<td>${leave.appliedOnFormatted}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:if>
		</div>
	</div>


	<div class="modal fade" id="quotaModal" tabindex="-1"
		aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered">
			<div class="modal-content">
				<div class="modal-header bg-warning">
					<h5 class="modal-title">Monthly Quota Exceeded</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"></button>
				</div>
				<div class="modal-body">You have already used all your allowed
					leaves for this month. Do you still want to apply more?</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-bs-dismiss="modal">No</button>
					<button type="button" class="btn btn-primary" id="proceedOverride">Yes</button>
				</div>
			</div>
		</div>
	</div>


	<div class="modal fade" id="reasonModal" tabindex="-1"
		aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered">
			<div class="modal-content">
				<div class="modal-header bg-info">
					<h5 class="modal-title">Provide Override Reason</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<p>
						This will deduct the extra leaves from your <strong>yearly
							balance</strong>.
					</p>
					<textarea id="overrideReasonInput" class="form-control"
						placeholder="Enter reason..." required></textarea>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-bs-dismiss="modal">Cancel</button>
					<button type="button" class="btn btn-success" id="submitOverride">Submit</button>
				</div>
			</div>
		</div>
	</div>





<!-- previous -->
				<div class="modal fade" id="overrideModal" tabindex="-1"
					aria-hidden="true">
					<div class="modal-dialog modal-dialog-centered">
						<div class="modal-content shadow-lg border-0 rounded-3">

							<div class="modal-header bg-danger text-white rounded-top-3">
								<h5 class="modal-title">
									<i class="bi bi-exclamation-triangle-fill me-2"></i> Exceeding
									Leave Limit
								</h5>
								<button type="button" class="btn-close btn-close-white"
									data-bs-dismiss="modal" aria-label="Close"></button>
							</div>

							<div class="modal-body">
								<div class="alert alert-warning d-flex align-items-center"
									role="alert">
									<i class="bi bi-info-circle-fill me-2"></i> <span
										id="overrideMessage"></span>
								</div>
							</div>

							<div class="modal-footer">
								<button type="button" class="btn btn-outline-secondary"
									data-bs-dismiss="modal">
									<i class="bi bi-x-circle"></i> Close
								</button>
							</div>

						</div>
					</div>
				</div>

				<script>
document.addEventListener("DOMContentLoaded", function() {
    var overrideModal = document.getElementById('overrideModal');
    overrideModal.addEventListener('show.bs.modal', function (event) {
        var button = event.relatedTarget;
        var reason = button.getAttribute('data-reason') || "No override reason provided";
        document.getElementById('overrideMessage').textContent = reason;
    });
});
</script>

	<script>
	
	
		function getLocalToday() {
	    const now = new Date();
	    const year = now.getFullYear();
	    const month = String(now.getMonth() + 1).padStart(2, "0");
	    const day = String(now.getDate()).padStart(2, "0");
	    
	    const today = year + "-" + month + "-" + day;
	    
	    return today;
	}


	document.addEventListener("DOMContentLoaded", () => {
	    const fromDateInput = document.getElementById("dateFrom");
	    const toDateInput = document.getElementById("dateTo");
	    const today = getLocalToday();
	    

	    fromDateInput.min = today;
	    

	    
	    if (fromDateInput.value && fromDateInput.value < today) {
	        fromDateInput.value = "";
	    }

	    fromDateInput.addEventListener("change", function () {
	        toDateInput.min = fromDateInput.value;
	        if (toDateInput.value < fromDateInput.value) {
	            toDateInput.value = "";
	        }
	    });
	});
	
	
	<c:if test="${quotaExceeded}">
	document.addEventListener("DOMContentLoaded", function(){
	    // Scroll user to form
	    document.getElementById("leaveForm").scrollIntoView({behavior: "smooth"});
	
	    let quotaModal = new bootstrap.Modal(document.getElementById("quotaModal"));
	    let reasonModal = new bootstrap.Modal(document.getElementById("reasonModal"));
	
	    quotaModal.show();
	
	    document.getElementById("proceedOverride").addEventListener("click", function(){
	        quotaModal.hide();
	        reasonModal.show();
	    });
	
	    document.getElementById("submitOverride").addEventListener("click", function(){
	        let reason = document.getElementById("overrideReasonInput").value.trim();
	        if(reason){
	            let form = document.getElementById("leaveForm");
	            let input = document.createElement("input");
	            input.type = "hidden";
	            input.name = "override_reason";
	            input.value = reason;
	            form.appendChild(input);
	            form.submit();
	        } else {
	            alert("Please enter a reason before submitting.");
	        }
	    });
	});
	</c:if>
	</script>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
