package com.aurionpro.model;

import java.math.BigDecimal;
import java.util.Date;

public class Transaction {
	 private int txnId;
	    private Date txnDate;
	    private String type;
	    private String status;
	    private BigDecimal amount;
	    private String description;

	    private String fromAccountNumber;
	    private String fromCustomerName;

	    private String toAccountNumber;
	    private String toCustomerName;

	    private String toExternalAccountNumber;
	    private String toExternalIfsc;

	    private String initiatedBy;

		public int getTxnId() {
			return txnId;
		}

		public void setTxnId(int txnId) {
			this.txnId = txnId;
		}

		public Date getTxnDate() {
			return txnDate;
		}

		public void setTxnDate(Date txnDate) {
			this.txnDate = txnDate;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public BigDecimal getAmount() {
			return amount;
		}

		public void setAmount(BigDecimal amount) {
			this.amount = amount;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getFromAccountNumber() {
			return fromAccountNumber;
		}

		public void setFromAccountNumber(String fromAccountNumber) {
			this.fromAccountNumber = fromAccountNumber;
		}

		public String getFromCustomerName() {
			return fromCustomerName;
		}

		public void setFromCustomerName(String fromCustomerName) {
			this.fromCustomerName = fromCustomerName;
		}

		public String getToAccountNumber() {
			return toAccountNumber;
		}

		public void setToAccountNumber(String toAccountNumber) {
			this.toAccountNumber = toAccountNumber;
		}

		public String getToCustomerName() {
			return toCustomerName;
		}

		public void setToCustomerName(String toCustomerName) {
			this.toCustomerName = toCustomerName;
		}

		public String getToExternalAccountNumber() {
			return toExternalAccountNumber;
		}

		public void setToExternalAccountNumber(String toExternalAccountNumber) {
			this.toExternalAccountNumber = toExternalAccountNumber;
		}

		public String getToExternalIfsc() {
			return toExternalIfsc;
		}

		public void setToExternalIfsc(String toExternalIfsc) {
			this.toExternalIfsc = toExternalIfsc;
		}

		public String getInitiatedBy() {
			return initiatedBy;
		}

		public void setInitiatedBy(String initiatedBy) {
			this.initiatedBy = initiatedBy;
		}

}
