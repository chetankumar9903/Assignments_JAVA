<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page session="true" %>

<div class="card p-4">
        <!--<h4 class="mb-3 text-success">Sanctioned Leaves</h4>  -->
         <c:if test="${empty limitExceededLeaves}">
				<div class="alert alert-info text-center">No leave requests exceeding the allowed monthly limit.</div>
			</c:if>
			<c:if test="${not empty limitExceededLeaves}">
        <table class="table table-hover table-striped align-middle">
            <thead class="table-dark">
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
                    <th>Applied On</th>
                </tr>
            </thead>
            <tbody>
            <c:forEach var="leave" items="${limitExceededLeaves}">
                <tr>
                    <td>${leave.employeeId}</td>
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
         <c:choose>
                            <c:when test="${leave.status == 'Approved'}">
                                <span class="badge bg-success">${leave.status}</span>
                            </c:when>
                            <c:when test="${leave.status == 'Rejected'}">
                                <span class="badge bg-danger">${leave.status}</span>
                            </c:when>
                            <c:otherwise>
                                <span class="badge bg-warning">${leave.status}</span>
                            </c:otherwise>
                        </c:choose>
    </div>
</td>
                    
                    <td>${leave.appliedOnFormatted}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        </c:if>
    </div>


