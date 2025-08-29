package com.aurionpro.model;

import java.sql.Timestamp;

public class Beneficiary {

	private int beneficiaryId;
    private int ownerAccountId;
    private String nickname;
    private String beneficiaryName;
    private Integer beneficiaryAccountId; // for internal
    private String beneficiaryAccountNumber; // for external
    private String beneficiaryIfscCode; // for external
    private boolean isInternal;
    private Timestamp createdAt;

    // Getters & Setters
    public int getBeneficiaryId() { return beneficiaryId; }
    public void setBeneficiaryId(int beneficiaryId) { this.beneficiaryId = beneficiaryId; }

    public int getOwnerAccountId() { return ownerAccountId; }
    public void setOwnerAccountId(int ownerAccountId) { this.ownerAccountId = ownerAccountId; }

    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }

    public String getBeneficiaryName() { return beneficiaryName; }
    public void setBeneficiaryName(String beneficiaryName) { this.beneficiaryName = beneficiaryName; }

    public Integer getBeneficiaryAccountId() { return beneficiaryAccountId; }
    public void setBeneficiaryAccountId(Integer beneficiaryAccountId) { this.beneficiaryAccountId = beneficiaryAccountId; }

    public String getBeneficiaryAccountNumber() { return beneficiaryAccountNumber; }
    public void setBeneficiaryAccountNumber(String beneficiaryAccountNumber) { this.beneficiaryAccountNumber = beneficiaryAccountNumber; }

    public String getBeneficiaryIfscCode() { return beneficiaryIfscCode; }
    public void setBeneficiaryIfscCode(String beneficiaryIfscCode) { this.beneficiaryIfscCode = beneficiaryIfscCode; }

    public boolean isInternal() { return isInternal; }
    public void setInternal(boolean isInternal) { this.isInternal = isInternal; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}