<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:if test="${activeTab=='pending'}">
    <div class="card shadow-sm p-4 mb-4">
        
         <div class="d-flex justify-content-between align-items-center mb-4">
            <h4 class=" mb-0"> Pending Account Applications <span class="badge bg-info text-dark ms-2">${pendingCount != null ? pendingCount : 0}</h4>
            <a href="${pageContext.request.contextPath}/admin_dashboard?tab=pending" class="btn btn-outline-secondary">
                <i class="bi bi-arrow-clockwise me-1"></i> Refresh
            </a>
        </div>
        

        <!-- Filters -->
        <form class="row g-2 mb-3" method="get" action="${pageContext.request.contextPath}/admin_dashboard">
            <input type="hidden" name="tab" value="pending" />
            
            <div class="col-md-3">
                <input type="text" name="q" class="form-control" placeholder="Search username" value="${fn:escapeXml(q)}" />
            </div>
            <div class="col-md-2">
                <input type="date" name="date_from" class="form-control" value="${date_from}" />
            </div>
            <div class="col-md-2">
                <input type="date" name="date_to" class="form-control" value="${date_to}" />
            </div>
            <div class="col-md-2">
                <select name="limit" class="form-select">
                    <option value="10" ${limit==10 ? 'selected': ''}>10</option>
                    <option value="25" ${limit==25 ? 'selected': ''}>25</option>
                    <option value="50" ${limit==50 ? 'selected': ''}>50</option>
                    <option value="100" ${limit==100 ? 'selected': ''}>100</option>
                </select>
            </div>
           <div class="col-md-3 d-flex justify-content-start gap-2">
    <button class="btn btn-primary w-100">
        <i class="bi bi-funnel-fill me-1"></i> Filter
    </button>
    <a href="${pageContext.request.contextPath}/admin_dashboard?tab=pending" 
       class="btn btn-outline-secondary w-100">
        <i class="bi bi-x-circle me-1"></i> Reset
    </a>
</div>

        </form>

        <!-- Table -->
        <div class="table-responsive shadow-sm rounded">
            <table class="table table-hover table-bordered align-middle text-center">
                <thead class="table-dark">
                    <tr>
                        <th>#App Id</th>
                        <th>Username</th>
                        <th>Account Type</th>
                        <th>Initial Deposit</th>
                        <th>Applied On</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${not empty pendingApps}">
                            <c:forEach var="app" items="${pendingApps}">
                                <tr class="align-middle">
                                    <td class="fw-bold">${app.application_id}</td>
                                    <td>${app.username}</td>
                                    <td>
                                        <span class="badge bg-warning text-dark">${app.account_type}</span>
                                    </td>
                                    <td>${app.initial_deposit}</td>
                                    <td>${app.created_at}</td>
                                   <td>
    <div class="d-flex justify-content-center gap-2">
        <button type="button" class="btn btn-success btn-sm" onclick="confirmApprove(${app.application_id})">
            <i class="bi bi-check-circle me-1"></i>Approve
        </button>
        <button type="button" class="btn btn-danger btn-sm" onclick="promptRejectApplication(${app.application_id})">
            <i class="bi bi-x-circle me-1"></i>Reject
        </button>
        <a class="btn btn-outline-secondary btn-sm" 
           href="${pageContext.request.contextPath}/ApplicationDetailsServlet?application_id=${app.application_id}">
            <i class="bi bi-eye me-1"></i>Details
        </a>
    </div>
</td>

                                </tr>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <tr>
                                <td colspan="6" class="text-center text-muted fst-italic py-3">
                                    No pending applications found.
                                </td>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
        </div>

        <div class="mt-2 text-muted fst-italic small">
            Approve will create account using stored procedure. Reject allows setting a reason.
        </div>
    </div>
</c:if>
