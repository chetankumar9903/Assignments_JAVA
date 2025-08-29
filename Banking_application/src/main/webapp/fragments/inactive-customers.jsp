<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:if test="${activeTab=='inactive'}">
    <div class="card shadow-sm p-4 mb-4">
        
        
         <div class="d-flex justify-content-between align-items-center mb-4">
            <h4 class=" mb-0">  Inactive Customers <span class="badge bg-warning text-dark ms-2">${inactiveCustomers != null ? fn:length(inactiveCustomers) : 0}</span>
            <small class="text-muted fst-italic">KYC Pending</small></h4>
            <a href="${pageContext.request.contextPath}/admin_dashboard?tab=inactive" class="btn btn-outline-secondary">
                <i class="bi bi-arrow-clockwise me-1"></i> Refresh
            </a>
        </div>
        

        <div class="table-responsive shadow-sm rounded">
            <table class="table table-hover table-bordered align-middle text-center">
                <thead class="table-dark">
                    <tr>
                        <th>User ID</th>
                        <th>Username</th>
                        <th>Email</th>
                        <th>Phone</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${not empty inactiveCustomers}">
                            <c:forEach var="u" items="${inactiveCustomers}">
                                <tr id="row-${u.userId}">
                                    <td class="fw-bold">${u.userId}</td>
                                    <td>${u.username}</td>
                                    <td>${u.email}</td>
                                    <td>${u.phone}</td>
                                    <td>
                                        <div class="d-flex justify-content-center gap-2 flex-wrap">
                                            <button class="btn btn-outline-primary btn-sm" 
                                                onclick="openDetailsModal(
                                                    '${u.userId}',
                                                    '${fn:escapeXml(u.username)}',
                                                    '${fn:escapeXml(u.email)}',
                                                    '${fn:escapeXml(u.phone)}',
                                                    '${fn:escapeXml(u.fullName)}',
                                                    '${fn:escapeXml(u.dob)}',
                                                    '${fn:escapeXml(u.address)}',
                                                    '${fn:escapeXml(u.kycStatus)}',
                                                    '${fn:escapeXml(u.createdAt)}',
                                                    '${fn:escapeXml(u.govtIdType)}',
                                                    '${fn:escapeXml(u.govtIdValue)}',
                                                    '${fn:escapeXml(u.remarks)}'
                                                )">
                                                <i class="bi bi-eye me-1"></i>Details
                                            </button>
                                            <button class="btn btn-success btn-sm"
                                                onclick="selectedUserId=${u.userId}; new bootstrap.Modal(document.getElementById('verifyModal')).show();">
                                                <i class="bi bi-check-circle me-1"></i>Verify & Activate
                                            </button>
                                            <button class="btn btn-danger btn-sm"
                                                onclick="selectedUserId=${u.userId}; new bootstrap.Modal(document.getElementById('rejectModal')).show();">
                                                <i class="bi bi-x-circle me-1"></i>Reject
                                            </button>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <tr>
                                <td colspan="5" class="text-center text-muted fst-italic py-3">
                                    No inactive customers pending KYC.
                                </td>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
        </div>

        <div class="mt-2 text-muted fst-italic small">
            Verify & Activate will approve KYC and make the account active. Reject allows setting a reason.
        </div>
    </div>
</c:if>
