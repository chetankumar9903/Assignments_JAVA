package com.aurionpro.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.aurionpro.database.DBConnection;
import com.aurionpro.model.User;

public class UserDao {
	
	private Connection conn;

	public UserDao() {
	    this.conn = DBConnection.getInstance().getConnection();
	    if (this.conn == null) {
	        throw new RuntimeException("Failed to connect to the database.");
	    }
	}
	
	
    public boolean registerUser(String fullName, String username, String password, String dept, String role) {
        String sql = "INSERT INTO users(full_name, username, password, dept, role) VALUES (?, ?, ?, ?, ?)";
        try {
             PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, fullName);
            ps.setString(2, username);
            ps.setString(3, password);
            ps.setString(4, dept);
            ps.setString(5, role);

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

   
    public String validateUser(String username, String password, String role) {
        String sql = "SELECT role FROM users WHERE username=? AND password=? AND role=?";
        try {
             PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, role);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("role");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;  // invalid
    }
    
    public int getUserIdByUsername(String username) {
        String sql = "SELECT id FROM users WHERE username=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1; // not found
    }
    
    public String getUserDepartment(String username) {
        String department = null;
        try  {
            String sql = "SELECT dept FROM users WHERE username = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                department = rs.getString("dept");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return department;
    }
    
    public String getUserFullname(String username) {
        String department = null;
        try  {
            String sql = "SELECT full_name FROM users WHERE username = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                department = rs.getString("full_name");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return department;
    }

    
    public User getUserByUsername(String username) {
        String sql = "SELECT id, username, full_name, dept, role FROM users WHERE username = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("full_name"),
                        rs.getString("dept"),
                        rs.getString("role")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    
    public boolean isUsernameTaken(String username) {
        boolean exists = false;
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
        try {
             PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                exists = rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return exists;
    }

}
