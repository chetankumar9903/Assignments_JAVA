package com.aurionpro.model;

import java.sql.Timestamp;

public class User {
    private int userId;
    private String username;
    private String password; // plain text per your request
    private String email;
    private String phone;
    private String role; // ADMIN or CUSTOMER
    private boolean active;
    private Timestamp createdAt;
    
    

    public User() {
		super();
	}
	public User(int userId, String username, String password, String email, String phone, String role, boolean active,
			Timestamp createdAt) {
		super();
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.email = email;
		this.phone = phone;
		this.role = role;
		this.active = active;
		this.createdAt = createdAt;
	}
	// getters / setters
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}