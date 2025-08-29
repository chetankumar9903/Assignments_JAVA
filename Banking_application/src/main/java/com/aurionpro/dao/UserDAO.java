package com.aurionpro.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.aurionpro.database.DBConnection;
import com.aurionpro.model.Customer;
import com.aurionpro.model.User;
import com.aurionpro.model.UserCustomer;

public class UserDAO {
	
	private Connection connection;

	public UserDAO() {
	    this.connection = DBConnection.getConnection();
	    if (this.connection == null) {
	        throw new RuntimeException("Failed to connect to the database.");
	    }
	}

    public boolean usernameExists(String username) throws SQLException {
        if (username == null || username.trim().isEmpty()) {
            return false;
        }
        
        String sql = "SELECT 1 FROM users WHERE username = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    // Register user and create customers row (if role=CUSTOMER)
    // returns generated user_id (>0) or -1 on error
    public int registerUserWithCustomer(User user, Customer customer) throws SQLException {
        if (user == null) {
            throw new SQLException("User cannot be null");
        }
        if ("CUSTOMER".equalsIgnoreCase(user.getRole()) && customer == null) {
            throw new SQLException("Customer details required for CUSTOMER role");
        }
        
        String insertUserSql = "INSERT INTO users(username, password, email, phone, role, is_active) VALUES (?, ?, ?, ?, ?, ?)";
        String insertCustomerSql = "INSERT INTO customers(customer_id, first_name, last_name, dob, address_line1, address_line2, city, state, postal_code, govt_id_type, govt_id_value, kyc_status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        connection.setAutoCommit(false);
        try (PreparedStatement psUser = connection.prepareStatement(insertUserSql, Statement.RETURN_GENERATED_KEYS)) {
            psUser.setString(1, user.getUsername());
            psUser.setString(2, user.getPassword()); // plain as requested
            psUser.setString(3, user.getEmail());
            psUser.setString(4, user.getPhone());
            psUser.setString(5, user.getRole());
            // if CUSTOMER -> set is_active = 0 so admin must approve; for ADMIN set 1
            psUser.setInt(6, "CUSTOMER".equalsIgnoreCase(user.getRole()) ? 0 : 1);

            int rows = psUser.executeUpdate();
            if (rows == 0) {
                connection.rollback();
                return -1;
            }
            
            int generatedUserId;
            try (ResultSet keys = psUser.getGeneratedKeys()) {
                if (keys.next()) {
                    generatedUserId = keys.getInt(1);
                } else {
                    connection.rollback();
                    return -1;
                }
            }

            if ("CUSTOMER".equalsIgnoreCase(user.getRole())) {
                try (PreparedStatement psCust = connection.prepareStatement(insertCustomerSql)) {
                    psCust.setInt(1, generatedUserId);
                    psCust.setString(2, customer.getFirstName());
                    psCust.setString(3, customer.getLastName());
                    psCust.setDate(4, customer.getDob());
                    psCust.setString(5, customer.getAddressLine1());
                    psCust.setString(6, customer.getAddressLine2());
                    psCust.setString(7, customer.getCity());
                    psCust.setString(8, customer.getState());
                    psCust.setString(9, customer.getPostalCode());
                    psCust.setString(10, customer.getGovtIdType());
                    psCust.setString(11, customer.getGovtIdValue());
                    psCust.setString(12, "PENDING");
                    psCust.executeUpdate();
                }
            }
            connection.commit();
            return generatedUserId;
            
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    // Login lookup
    public User findByUsername(String username) throws SQLException {
        if (username == null || username.trim().isEmpty()) {
            return null;
        }
        
        String sql = "SELECT user_id, username, password, email, phone, role, is_active, created_at FROM users WHERE username = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                
                User u = new User();
                u.setUserId(rs.getInt("user_id"));
                u.setUsername(rs.getString("username"));
                u.setPassword(rs.getString("password"));
                u.setEmail(rs.getString("email"));
                u.setPhone(rs.getString("phone"));
                u.setRole(rs.getString("role"));
                u.setActive(rs.getInt("is_active") == 1);
                u.setCreatedAt(rs.getTimestamp("created_at"));
                return u;
            }
        }
    }

    // Validate login (plain password compare)
    public User validateLogin(String username, String password, String expectedRole) throws SQLException {
        if (username == null || password == null) {
            return null;
        }
        
        User u = findByUsername(username);
        if (u == null) return null;
        if (!u.isActive()) return null;
        if (expectedRole != null && !expectedRole.equalsIgnoreCase(u.getRole())) return null;
        // plaintext comparison
        if (!password.equals(u.getPassword())) return null;
        return u;
    }
    
/**    
 // Activate a customer
    public boolean activateCustomer(int userId) {
        String sql = "UPDATE users SET is_active = 1 WHERE user_id = ? AND role='CUSTOMER' AND is_active=0";
        try (
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            int updated = ps.executeUpdate();
            return updated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
    public List<User> getInactiveCustomers() {
        String sql = "SELECT user_id, username, email FROM users WHERE role='CUSTOMER' AND is_active=0";
        List<User> list = new ArrayList<>();
        try (
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                User u = new User();
                u.setUserId(rs.getInt("user_id"));
                u.setUsername(rs.getString("username"));
                u.setEmail(rs.getString("email"));
                list.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    **/
    
    
    // main
    
//    public List<User> getInactiveAndKycPendingCustomers() {
//        String sql = "SELECT u.user_id, u.username, u.email ,u.phone " +
//                     "FROM users u JOIN customers c ON u.user_id=c.customer_id " +
//                     "WHERE u.role='CUSTOMER' AND u.is_active=0 AND c.kyc_status='PENDING'";
//        List<User> list = new ArrayList<>();
//        try (
//             PreparedStatement ps = connection.prepareStatement(sql);
//             ResultSet rs = ps.executeQuery()) {
//            while (rs.next()) {
//                User u = new User();
//                u.setUserId(rs.getInt("user_id"));
//                u.setUsername(rs.getString("username"));
//                u.setEmail(rs.getString("email"));
//                u.setPhone(rs.getString("phone"));
//                list.add(u);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return list;
//    }

    
    public boolean verifyKycAndActivateCustomer(int userId) {
        String sql = "UPDATE users u JOIN customers c ON u.user_id = c.customer_id " +
                     "SET u.is_active = 1, c.kyc_status = 'VERIFIED' " +
                     "WHERE u.user_id = ? AND u.is_active = 0 AND c.kyc_status = 'PENDING'";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            int updated = ps.executeUpdate();
            return updated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    
    public boolean rejectCustomer(int userId, String remarks) {
        String sql = "UPDATE customers SET kyc_status='REJECTED', remarks=? " +
                     "WHERE customer_id=? AND kyc_status='PENDING'";
        try (
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, remarks == null ? "" : remarks);
            ps.setInt(2, userId);
            int updated = ps.executeUpdate();
            return updated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public List<UserCustomer> getInactiveAndKycPendingCustomers() {
        List<UserCustomer> list = new ArrayList<>();
        String sql = "SELECT u.user_id, u.username, u.email, u.phone, u.role, u.is_active, u.created_at, " +
                     "c.first_name, c.last_name, c.dob, c.address_line1, c.address_line2, " +
                     "c.city, c.state, c.postal_code, c.govt_id_type, c.govt_id_value, " +
                     "c.kyc_status, c.remarks, c.updated_at " +
                     "FROM users u " +
                     "JOIN customers c ON u.user_id = c.customer_id " +
                     "WHERE u.is_active = 0 AND c.kyc_status = 'PENDING'";

        try (
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                UserCustomer u = new UserCustomer();
                u.setUserId(rs.getInt("user_id"));
                u.setUsername(rs.getString("username"));
                u.setEmail(rs.getString("email"));
                u.setPhone(rs.getString("phone"));
                u.setRole(rs.getString("role"));
                u.setIsActive(rs.getInt("is_active"));
                u.setCreatedAt(rs.getString("created_at"));

                u.setFirstName(rs.getString("first_name"));
                u.setLastName(rs.getString("last_name"));
                u.setDob(rs.getString("dob"));
                u.setAddressLine1(rs.getString("address_line1"));
                u.setAddressLine2(rs.getString("address_line2"));
                u.setCity(rs.getString("city"));
                u.setState(rs.getString("state"));
                u.setPostalCode(rs.getString("postal_code"));
                u.setGovtIdType(rs.getString("govt_id_type"));
                u.setGovtIdValue(rs.getString("govt_id_value"));
                u.setKycStatus(rs.getString("kyc_status"));
                u.setRemarks(rs.getString("remarks"));
                u.setUpdatedAt(rs.getString("updated_at"));

                list.add(u);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}