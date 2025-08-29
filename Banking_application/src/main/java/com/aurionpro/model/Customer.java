package com.aurionpro.model;

import java.sql.Date;

public class Customer {
    private int customerId; // will be set = created user_id
    private String firstName;
    private String lastName;
    private Date dob;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String postalCode;
    private String govtIdType;
    private String govtIdValue;
    private String kycStatus; // PENDING, VERIFIED, REJECTED
    
    

    public Customer() {
		super();
	}
	public Customer(int customerId, String firstName, String lastName, Date dob, String addressLine1,
			String addressLine2, String city, String state, String postalCode, String govtIdType, String govtIdValue,
			String kycStatus) {
		super();
		this.customerId = customerId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dob = dob;
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.city = city;
		this.state = state;
		this.postalCode = postalCode;
		this.govtIdType = govtIdType;
		this.govtIdValue = govtIdValue;
		this.kycStatus = kycStatus;
	}
	// getters & setters
    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public Date getDob() { return dob; }
    public void setDob(Date dob) { this.dob = dob; }
    public String getAddressLine1() { return addressLine1; }
    public void setAddressLine1(String addressLine1) { this.addressLine1 = addressLine1; }
    public String getAddressLine2() { return addressLine2; }
    public void setAddressLine2(String addressLine2) { this.addressLine2 = addressLine2; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getState() { return state; }
    public void setState(String state) { this.state = state; }
    public String getPostalCode() { return postalCode; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }
    public String getGovtIdType() { return govtIdType; }
    public void setGovtIdType(String govtIdType) { this.govtIdType = govtIdType; }
    public String getGovtIdValue() { return govtIdValue; }
    public void setGovtIdValue(String govtIdValue) { this.govtIdValue = govtIdValue; }
    public String getKycStatus() { return kycStatus; }
    public void setKycStatus(String kycStatus) { this.kycStatus = kycStatus; }
}