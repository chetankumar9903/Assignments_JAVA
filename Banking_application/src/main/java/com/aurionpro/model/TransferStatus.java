package com.aurionpro.model;

public enum TransferStatus {
    SUCCESS("Transfer completed successfully"),
    NO_ACCOUNT("No active account found for this customer"),
    NO_BENEFICIARY("Beneficiary not found"),
    INSUFFICIENT_FUNDS("Insufficient balance"),
    ERROR("Unexpected error occurred"),
    FAILED("Transfer failed"),
	NEGATIVE("zero or negative amount cannot credited"),
	EXTERNAL("External beneficiary missing accountNo/IFSC"),
	INTERNAL("Internal beneficiary missing beneficiary_account_id");

    private final String message;

    TransferStatus(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

