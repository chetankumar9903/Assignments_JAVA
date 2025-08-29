<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

	<!-- Customer Details Modal -->
	<div class="modal fade" id="detailsModal" tabindex="-1"
		aria-labelledby="detailsModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg modal-dialog-centered">
			<div class="modal-content">
				<div class="modal-header bg-primary text-white">
					<h5 class="modal-title" id="detailsModalLabel">Customer
						Details</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
				</div>
				<div class="modal-body">
					<dl class="row">
						<dt class="col-sm-3">User ID</dt>
						<dd class="col-sm-9" id="detailUserId"></dd>
						<dt class="col-sm-3">Username</dt>
						<dd class="col-sm-9" id="detailUsername"></dd>
						<dt class="col-sm-3">Full Name</dt>
						<dd class="col-sm-9" id="detailFullName"></dd>
						<dt class="col-sm-3">Email</dt>
						<dd class="col-sm-9" id="detailEmail"></dd>
						<dt class="col-sm-3">Phone</dt>
						<dd class="col-sm-9" id="detailPhone"></dd>
						<dt class="col-sm-3">Date of Birth</dt>
						<dd class="col-sm-9" id="detailDob"></dd>
						<dt class="col-sm-3">Address</dt>
						<dd class="col-sm-9" id="detailAddress"></dd>
						<dt class="col-sm-3">KYC Status</dt>
						<dd class="col-sm-9">
							<span id="detailKyc" class="badge"></span>
						</dd>
						<dt class="col-sm-3">Government ID</dt>
						<dd class="col-sm-9" id="detailGovtId"></dd>
						<dt class="col-sm-3">Remarks</dt>
						<dd class="col-sm-9" id="detailRemarks"></dd>
						<dt class="col-sm-3">Created At</dt>
						<dd class="col-sm-9" id="detailCreated"></dd>
					</dl>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-success" id="openVerifyModal">Verify
						& Activate</button>
					<button type="button" class="btn btn-danger" id="openRejectModal">Reject</button>
					<button type="button" class="btn btn-secondary"
						data-bs-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>

	<!-- Verify Confirmation Modal -->
	<div class="modal fade" id="verifyModal" tabindex="-1"
		aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered">
			<div class="modal-content">
				<div class="modal-header bg-success text-white">
					<h5 class="modal-title">Confirm Verification</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
				</div>
				<div class="modal-body">
					Are you sure you want to <strong>Verify & Activate</strong> this
					customer?
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-bs-dismiss="modal">Cancel</button>
					<button type="button" class="btn btn-success" id="confirmVerifyBtn">Yes,
						Verify</button>
				</div>
			</div>
		</div>
	</div>

	<!-- Reject Confirmation Modal -->
	<div class="modal fade" id="rejectModal" tabindex="-1"
		aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered">
			<div class="modal-content">
				<div class="modal-header bg-danger text-white">
					<h5 class="modal-title">Reject Customer</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
				</div>
				<div class="modal-body">
					<input type="hidden" id="rejectUserId">
					<p>Please provide a reason for rejection:</p>
					<textarea id="rejectRemarks" class="form-control" rows="3"
						placeholder="Enter reason for rejection..." required></textarea>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-bs-dismiss="modal">Cancel</button>
					<button type="button" class="btn btn-danger" id="confirmRejectBtn">Reject</button>
				</div>
			</div>
		</div>
	</div>

	<!-- Toast Notification -->
	<div class="position-fixed top-0 end-0 p-3" style="z-index: 1055;">
		<div id="actionToast"
			class="toast align-items-center text-white bg-success border-0"
			role="alert" aria-live="assertive" aria-atomic="true">
			<div class="d-flex">
				<div class="toast-body" id="toastMessage">Action successful!</div>
				<button type="button" class="btn-close btn-close-white me-2 m-auto"
					data-bs-dismiss="toast"></button>
			</div>
		</div>
	</div>
	
	
	<!-- Approved Customer Details Modal -->
<div class="modal fade" id="approvedDetailsModal" tabindex="-1" aria-hidden="true">
  <div class="modal-dialog modal-lg modal-dialog-centered">
    <div class="modal-content shadow-sm">
      
      <div class="modal-header bg-primary text-white">
        <h5 class="modal-title">Approved Customer Details</h5>
        <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
      </div>
      
      <div class="modal-body">
        <!-- User Info -->
        <h6 class="text-secondary">User Info</h6>
        <dl class="row mb-3">
          <dt class="col-sm-3">User ID</dt><dd class="col-sm-9" id="approvedUserId"></dd>
          <dt class="col-sm-3">Username</dt><dd class="col-sm-9" id="approvedUsername"></dd>
          <dt class="col-sm-3">Email</dt><dd class="col-sm-9" id="approvedEmail"></dd>
          <dt class="col-sm-3">Phone</dt><dd class="col-sm-9" id="approvedPhone"></dd>
        </dl>

        <!-- Customer Info -->
        <h6 class="text-secondary">Customer Info</h6>
        <dl class="row mb-3">
          <dt class="col-sm-3">Full Name</dt><dd class="col-sm-9" id="approvedFullName"></dd>
          <dt class="col-sm-3">Date of Birth</dt><dd class="col-sm-9" id="approvedDob"></dd>
          <dt class="col-sm-3">Address</dt><dd class="col-sm-9" id="approvedAddress"></dd>
          <dt class="col-sm-3">Govt ID Type</dt><dd class="col-sm-9" id="approvedGovtIdType"></dd>
          <dt class="col-sm-3">Govt ID Value</dt><dd class="col-sm-9" id="approvedGovtIdValue"></dd>
          <dt class="col-sm-3">KYC Status</dt><dd class="col-sm-9" id="approvedKyc"></dd>
        </dl>

        <!-- Account Info -->
        <h6 class="text-secondary">Account Info</h6>
        <dl class="row mb-3">
          <dt class="col-sm-3">Account Number</dt><dd class="col-sm-9" id="approvedAccNo"></dd>
          <dt class="col-sm-3">Account Type</dt><dd class="col-sm-9" id="approvedAccType"></dd>
          <dt class="col-sm-3">Balance</dt><dd class="col-sm-9" id="approvedBalance"></dd>
          <dt class="col-sm-3">Account Status</dt><dd class="col-sm-9" id="approvedAccStatus"></dd>
          <dt class="col-sm-3">IFSC Code</dt><dd class="col-sm-9" id="approvedIfsc"></dd>
          <dt class="col-sm-3">Bank Name</dt><dd class="col-sm-9" id="approvedBankName"></dd>
          <dt class="col-sm-3">Branch Name</dt><dd class="col-sm-9" id="approvedBranchName"></dd>
          <dt class="col-sm-3">Branch City</dt><dd class="col-sm-9" id="approvedBranchCity"></dd>
        </dl>

        <!-- Application Info -->
        <h6 class="text-secondary">Application Info</h6>
        <dl class="row mb-0">
          <dt class="col-sm-3">Application ID</dt><dd class="col-sm-9" id="approvedAppId"></dd>
          <dt class="col-sm-3">Initial Deposit</dt><dd class="col-sm-9" id="approvedInitDeposit"></dd>
          <dt class="col-sm-3">Applied On</dt><dd class="col-sm-9" id="approvedAppCreated"></dd>
          <dt class="col-sm-3">Status</dt><dd class="col-sm-9" id="approvedAppStatus"></dd>
          <dt class="col-sm-3">Reviewed By</dt><dd class="col-sm-9" id="approvedReviewedBy"></dd>
          <dt class="col-sm-3">Reviewed By Name</dt><dd class="col-sm-9" id="approvedReviewedByName"></dd>
          <dt class="col-sm-3">Reviewed At</dt><dd class="col-sm-9" id="approvedReviewedAt"></dd>
          <dt class="col-sm-3">Remarks</dt><dd class="col-sm-9" id="approvedRemarks"></dd>
        </dl>
      </div>

      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
      </div>

    </div>
  </div>
</div>

	
	
	
	
	


	<!-- this is for details in pending application for this new page is already made -->
	<!-- Pending Application Details Modal -->
	<div class="modal fade" id="appDetailsModal" tabindex="-1"
		aria-labelledby="appDetailsModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg modal-dialog-centered">
			<div class="modal-content">
				<div class="modal-header bg-primary text-white">
					<h5 class="modal-title" id="appDetailsModalLabel">Application
						Details</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
				</div>
				<div class="modal-body">
					<dl class="row">
						<dt class="col-sm-3">Application ID</dt>
						<dd class="col-sm-9" id="appDetailId"></dd>
						<dt class="col-sm-3">Username</dt>
						<dd class="col-sm-9" id="appDetailUsername"></dd>
						<dt class="col-sm-3">Full Name</dt>
						<dd class="col-sm-9" id="appDetailFullName"></dd>
						<dt class="col-sm-3">Email</dt>
						<dd class="col-sm-9" id="appDetailEmail"></dd>
						<dt class="col-sm-3">Phone</dt>
						<dd class="col-sm-9" id="appDetailPhone"></dd>
						<dt class="col-sm-3">Account Type</dt>
						<dd class="col-sm-9" id="appDetailAccountType"></dd>
						<dt class="col-sm-3">Initial Deposit</dt>
						<dd class="col-sm-9" id="appDetailDeposit"></dd>
						<dt class="col-sm-3">Applied On</dt>
						<dd class="col-sm-9" id="appDetailAppliedOn"></dd>
						<dt class="col-sm-3">Remarks</dt>
						<dd class="col-sm-9" id="appDetailRemarks"></dd>
					</dl>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-approve" id="appApproveBtn">Approve</button>
					<button type="button" class="btn btn-reject" id="appRejectBtn">Reject</button>
					<button type="button" class="btn btn-secondary"
						data-bs-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>
