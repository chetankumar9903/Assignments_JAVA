<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page session="true"%>

<!DOCTYPE html>
<html>
<head>
<title>Admin Dashboard</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css"
	rel="stylesheet">
<style>
body {
	background: #f8f9fa;
	font-family: 'Segoe UI', sans-serif;
}

.dashboard-cards .card {
	border-radius: 12px;
	cursor: pointer;
	transition: transform 0.2s ease, box-shadow 0.2s ease;
}

.dashboard-cards .card:hover {
	transform: translateY(-5px);
	box-shadow: 0px 8px 20px rgba(0, 0, 0, 0.15);
}

.dashboard-section {
	margin-top: 30px;
}

.dashboard-section h4 {
	font-weight: 600;
}

.hidden {
	display: none !important;
}

.container {
	margin-top: 70px;
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

.card {
	border-radius: 15px;
	box-shadow: 0px 4px 15px rgba(0, 0, 0, 0.1);
	margin-bottom: 30px;
	text-align: center;
}

h4 {
	font-weight: 600;
}

.btn-approve {
	background-color: #28a745;
	color: white;
	border-radius: 8px;
}

.btn-reject {
	background-color: #dc3545;
	color: white;
	border-radius: 8px;
}

.btn-logout {
	background-color: #6c757d;
	color: white;
	border-radius: 8px;
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

table th, table td {
	vertical-align: middle;
}

@keyframes flash {
  0% {
    background-color: #FFD966;
  }
  50% {
    background-color: #FFF2CC;
  }
  100% {
    background-color: #FFD966;
  }
}

.new-leave {
	animation: flash 1.5s ease-in-out 3;
}


@keyframes flash {
  0%, 100% {
    opacity: 1;
  }
  50% {
    opacity: 0;
  }
}
.new-badge {
	background-color: #0d6efd;
	color: white;
	font-size: 0.7rem;
	font-weight: bold;
	padding: 2px 6px;
	border-radius: 12px;
	position: absolute;
	top: -10px;
	left: -250%;
	transform: translateX(-50%);
	animation: flash 1s infinite;
	box-shadow: 0 2px 6px rgba(0, 0, 0, 0.2);
	z-index: 10;
}

.serial-number {
	position: relative;
	/*  display: inline-block;*/
	padding-top: 10px;
}

@media ( min-width : 992px) {
	.dashboard-cards .col-lg-2-4 {
		flex: 0 0 19%;
		max-width: 19%;
	}
}

@media ( max-width : 991.98px) {
	.dashboard-cards .col-lg-2-4 {
		flex: 0 0 calc(33.333% - 1rem);
		max-width: calc(33.333% - 1rem);
	}
}

@media ( max-width : 575.98px) {
	.dashboard-cards .col-lg-2-4 {
		flex: 0 0 calc(50% - 0.5rem);
		max-width: calc(50% - 0.5rem);
	}
}
</style>
</head>
<body>
	<jsp:include page="navbar.jsp" />

	<c:if test="${not empty sessionScope.successMsg}">
		<div id="success-alert" class="alert alert-success">
			${sessionScope.successMsg}</div>
		<c:remove var="successMsg" scope="session" />
	</c:if>

	<script>
    const alertBox = document.getElementById("success-alert");
    if (alertBox) {
        setTimeout(() => { alertBox.style.right = "20px"; }, 100);
        setTimeout(() => { alertBox.style.right = "-400px"; }, 5000);
    }
</script>


	<div class="container py-4">

		
		<div class="text-center mb-4">
			<h2 class="fw-bold text-primary">
				<i class="bi bi-speedometer2 me-2"></i> Admin Dashboard
			</h2>
			<p class="text-muted">Manage employee leave applications at a
				glance</p>
		</div>

		<div class="container mt-5">
			<div class="row g-3 justify-content-center dashboard-cards">
				
				<div class="col-6 col-sm-4 col-lg-2-4">
					<div class="card text-center p-3 bg-warning text-dark"
						data-section="pending"
						style="min-height: 180px; min-width: 200px;">
						<i class="bi bi-hourglass-split fs-1 mb-2"></i>
						<h5>Pending</h5>
						<h4>${fn:length(pendingLeaves)}</h4>
					</div>
				</div>

				
				<div class="col-6 col-sm-4 col-lg-2-4">
					<div class="card text-center p-3 bg-success text-white"
						data-section="sanctioned"
						style="min-height: 180px; min-width: 200px;">
						<i class="bi bi-check2-circle fs-1 mb-2"></i>
						<h5>Sanctioned</h5>
						<h4>${fn:length(sanctionedLeaves)}</h4>
					</div>
				</div>

				
				<div class="col-6 col-sm-4 col-lg-2-4">
					<div class="card text-center p-3 bg-primary text-white"
						data-section="approved"
						style="min-height: 180px; min-width: 200px;">
						<i class="bi bi-check-circle fs-1 mb-2"></i>
						<h5>Approved</h5>
						<h4>${fn:length(approvedLeaves)}</h4>
					</div>
				</div>

				
				<div class="col-6 col-sm-4 col-lg-2-4">
					<div class="card text-center p-3 bg-danger text-white"
						data-section="rejected"
						style="min-height: 180px; min-width: 200px;">
						<i class="bi bi-x-circle fs-1 mb-2"></i>
						<h5>Rejected</h5>
						<h4>${fn:length(rejectedLeaves)}</h4>
					</div>
				</div>

				
				<div class="col-6 col-sm-4 col-lg-2-4">
					<div class="card text-center p-3 bg-secondary text-white"
						data-section="limit" style="min-height: 180px; min-width: 200px;">
						<i class="bi bi-exclamation-triangle fs-1 mb-2"></i>
						<h5>Limit Exceeded</h5>
						<h4>${fn:length(limitExceededLeaves)}</h4>
					</div>
				</div>
			</div>

</div>


			
			<div id="section-pending" class="dashboard-section hidden">
				<h4 class="text-warning mb-3">
					<i class="bi bi-hourglass-split me-2"></i> Pending Leaves
				</h4>
				
				<jsp:include page="pendingleaves.jsp" />
			</div>

			
			<div id="section-sanctioned" class="dashboard-section hidden">
				<h4 class="text-success mb-3">
					<i class="bi bi-check2-circle me-2"></i> Sanctioned Leaves
				</h4>

				<jsp:include page="sanctionedleaves.jsp" />
			</div>


			<div id="section-approved" class="dashboard-section hidden">
				<h4 class="text-primary mb-3">
					<i class="bi bi-check-circle me-2"></i> Approved Leaves
				</h4>
				<jsp:include page="approvedleaves.jsp" />
				</div>
				
				<div id="section-rejected" class="dashboard-section hidden">
					<h4 class="text-danger mb-3">
						<i class="bi bi-x-circle me-2"></i> Rejected Leaves
					</h4>
					<jsp:include page="rejectedleaves.jsp" />

</div>

				
					<div id="section-limit" class="dashboard-section hidden">
						<h4 class="text-secondary mb-3">
							<i class="bi bi-exclamation-triangle me-2"></i> Limit Exceeded
							Leaves
						</h4>
						<jsp:include page="limitexceedleaves.jsp" />
						
					</div>

				</div>

</div>
				
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


				<script
					src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
				<script>
document.querySelectorAll(".dashboard-cards .card").forEach(card => {
    card.addEventListener("click", () => {
        const section = card.getAttribute("data-section");
        document.querySelectorAll(".dashboard-section").forEach(sec => sec.classList.add("hidden"));
        document.getElementById("section-" + section).classList.remove("hidden");
        window.scrollTo({ top: 200, behavior: 'smooth' });
    });
});
</script>
</body>
</html>
