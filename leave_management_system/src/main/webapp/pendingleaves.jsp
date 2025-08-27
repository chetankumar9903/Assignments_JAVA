<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page session="true" %>

<style>
/* Card styling */
.card {
    border-radius: 15px;
    box-shadow: 0 6px 20px rgba(0,0,0,0.08);
    border: none;
}

/* Table header */
.table thead th {
    background: linear-gradient(90deg, #0d6efd, #6610f2);
    color: #fff;
    font-weight: 600;
    text-align: center;
    vertical-align: middle;
    border: none;
}

/* Table rows */
.table tbody tr {
    transition: all 0.2s ease-in-out;
}
.table tbody tr:hover {
    background-color: #f1f7ff;
    transform: scale(1.01);
}

/* Serial number & badge */
.serial-number {
    font-weight: 600;
    color: #0d6efd;
}
.new-badge {
    background: #ffc107;
    color: #212529;
    font-size: 0.75rem;
    padding: 3px 6px;
    border-radius: 6px;
    margin-right: 6px;
}

/* Status badges */
.badge {
    font-size: 0.8rem;
    padding: 6px 10px;
    border-radius: 8px;
}
.badge.bg-warning {
    background-color: #ffc107 !important;
    color: #212529;
}
.badge.bg-danger {
    background-color: #dc3545 !important;
}
.badge.bg-success {
    background-color: #28a745 !important;
}

/* Buttons */
.btn-approve {
    background: linear-gradient(90deg, #28a745, #20c997);
    border: none;
    color: white;
    padding: 5px 12px;
    border-radius: 8px;
    transition: all 0.2s ease;
}
.btn-approve:hover {
    background: linear-gradient(90deg, #20c997, #28a745);
    transform: translateY(-2px);
}

.btn-reject {
    background: linear-gradient(90deg, #dc3545, #fd7e14);
    border: none;
    color: white;
    padding: 5px 12px;
    border-radius: 8px;
    transition: all 0.2s ease;
}
.btn-reject:hover {
    background: linear-gradient(90deg, #fd7e14, #dc3545);
    transform: translateY(-2px);
}
</style>

<div class="card p-4">

<c:if test="${empty pendingLeaves}">
				<div class="alert alert-info text-center">No pending leave applications at the moment.</div>
			</c:if>
			<c:if test="${not empty pendingLeaves}">
    <table class="table table-hover align-middle text-center">
        <thead>
            <tr>
                <th>Emp ID</th>
                <th>Emp Name</th>
                <th>Dept</th>
                <th>Type</th>
                <th>From</th>
                <th>To</th>
                <th>No. of Days</th>
                <th>Reason</th>
                <th>Status</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="leave" items="${pendingLeaves}">
                <tr>
                    <td>
                        <span class="serial-number">
                            <c:if test="${leave.newLeave}">
                                <span class="new-badge">New</span>
                            </c:if>
                            ${leave.employeeId}
                        </span>
                    </td>
                    <td>${leave.employeeName}</td>
                    <td>${leave.department}</td>
                    <td>${leave.leaveTypeName}</td>
                    <td>${leave.dateFromFormatted}</td>
                    <td>${leave.dateToFormatted}</td>
                    <td>${leave.noOfDays}</td>
                    <td>${leave.reason}</td>
                    <td>
                        <c:if test="${leave.exceedingLimit or not empty leave.overrideReason}">
                            <span class="badge bg-danger"
                                  style="cursor:pointer;"
                                  data-bs-toggle="modal"
                                  data-bs-target="#overrideModal"
                                  data-reason="${leave.overrideReason}">
                                Exceeds Limit
                            </span>
                        </c:if>
                        <div class="mt-1">
                            <span class="badge bg-warning">${leave.status}</span>
                        </div>
                    </td>
                    <td>
                        <form action="AdminLeaveController" method="post" style="display:inline;">
                            <input type="hidden" name="leaveId" value="${leave.leaveId}">
                            <input type="hidden" name="action" value="approve">
                            <button type="submit" class="btn btn-sm btn-approve">Approve</button>
                        </form>
                        <form action="AdminLeaveController" method="post" style="display:inline;">
                            <input type="hidden" name="leaveId" value="${leave.leaveId}">
                            <input type="hidden" name="action" value="reject">
                            <button type="submit" class="btn btn-sm btn-reject">Reject</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    </c:if>
</div>
