package com.aurionpro.model;

public class UserCustomer {
	
	 private int userId;
	    private String username;
	    private String email;
	    private String phone;
	    private String role;
	    private int isActive;
	    private String createdAt;

	    // Customer details
	    private String firstName;
	    private String lastName;
	    private String dob;
	    private String addressLine1;
	    private String addressLine2;
	    private String city;
	    private String state;
	    private String postalCode;
	    private String govtIdType;
	    private String govtIdValue;
	    private String kycStatus;
	    private String remarks;
	    private String updatedAt;
	    
	    
	    
	    public int getUserId() {
			return userId;
		}



		public void setUserId(int userId) {
			this.userId = userId;
		}



		public String getUsername() {
			return username;
		}



		public void setUsername(String username) {
			this.username = username;
		}



		public String getEmail() {
			return email;
		}



		public void setEmail(String email) {
			this.email = email;
		}



		public String getPhone() {
			return phone;
		}



		public void setPhone(String phone) {
			this.phone = phone;
		}



		public String getRole() {
			return role;
		}



		public void setRole(String role) {
			this.role = role;
		}



		public int getIsActive() {
			return isActive;
		}



		public void setIsActive(int isActive) {
			this.isActive = isActive;
		}



		public String getCreatedAt() {
			return createdAt;
		}



		public void setCreatedAt(String createdAt) {
			this.createdAt = createdAt;
		}



		public String getFirstName() {
			return firstName;
		}



		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}



		public String getLastName() {
			return lastName;
		}



		public void setLastName(String lastName) {
			this.lastName = lastName;
		}



		public String getDob() {
			return dob;
		}



		public void setDob(String dob) {
			this.dob = dob;
		}



		public String getAddressLine1() {
			return addressLine1;
		}



		public void setAddressLine1(String addressLine1) {
			this.addressLine1 = addressLine1;
		}



		public String getAddressLine2() {
			return addressLine2;
		}



		public void setAddressLine2(String addressLine2) {
			this.addressLine2 = addressLine2;
		}



		public String getCity() {
			return city;
		}



		public void setCity(String city) {
			this.city = city;
		}



		public String getState() {
			return state;
		}



		public void setState(String state) {
			this.state = state;
		}



		public String getPostalCode() {
			return postalCode;
		}



		public void setPostalCode(String postalCode) {
			this.postalCode = postalCode;
		}



		public String getGovtIdType() {
			return govtIdType;
		}



		public void setGovtIdType(String govtIdType) {
			this.govtIdType = govtIdType;
		}



		public String getGovtIdValue() {
			return govtIdValue;
		}



		public void setGovtIdValue(String govtIdValue) {
			this.govtIdValue = govtIdValue;
		}



		public String getKycStatus() {
			return kycStatus;
		}



		public void setKycStatus(String kycStatus) {
			this.kycStatus = kycStatus;
		}



		public String getRemarks() {
			return remarks;
		}



		public void setRemarks(String remarks) {
			this.remarks = remarks;
		}



		public String getUpdatedAt() {
			return updatedAt;
		}



		public void setUpdatedAt(String updatedAt) {
			this.updatedAt = updatedAt;
		}



		public String getFullName() {
	        return (firstName + " " + lastName).trim();
	    }
		
		public String getAddress() {
		    StringBuilder sb = new StringBuilder();
		    if (addressLine1 != null) sb.append(addressLine1).append(" ");
		    if (addressLine2 != null) sb.append(addressLine2).append(" ");
		    if (city != null) sb.append(city).append(", ");
		    if (state != null) sb.append(state).append(" ");
		    if (postalCode != null) sb.append(postalCode);
		    return sb.toString().trim();
		}
	    

}
