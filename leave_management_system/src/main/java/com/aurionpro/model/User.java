package com.aurionpro.model;

public class User {
	private int id;
    private String username;
    private String password;
    private String fullname;
    private String role;
    private String dept;

    
    public User() {}
    
    public User(int id, String username, String fullname, String dept, String role) {
        this.id = id;
        this.username = username;
        this.fullname = fullname;
        this.dept = dept;
        this.role = role;
    }

    public User(int id, String username, String password, String role, String dept, String fullname) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.dept = dept;
        this.fullname = fullname;
    }
    

    public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", username=" + username + ", role=" + role + ", dept=" + dept + "]";
    }

}
