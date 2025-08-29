package com.aurionpro.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.aurionpro.database.DBConnection;
import com.aurionpro.model.CustomerProfile;

public class CustomerDAO {
	public CustomerProfile getProfileByUserId(int userId) {
        CustomerProfile profile = null;
        String sql = "SELECT u.user_id, u.username, u.email, u.phone, " +
                     "c.first_name, c.last_name, c.dob, c.address_line1, c.address_line2, " +
                     "c.city, c.state, c.postal_code, c.govt_id_type, c.govt_id_value, c.kyc_status " +
                     "FROM users u JOIN customers c ON u.user_id = c.customer_id " +
                     "WHERE u.user_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                profile = new CustomerProfile();
                profile.setUserId(rs.getInt("user_id"));
                profile.setUsername(rs.getString("username"));
                profile.setEmail(rs.getString("email"));
                profile.setPhone(rs.getString("phone"));
                profile.setFirstName(rs.getString("first_name"));
                profile.setLastName(rs.getString("last_name"));
                profile.setDob(rs.getString("dob"));
                profile.setAddressLine1(rs.getString("address_line1"));
                profile.setAddressLine2(rs.getString("address_line2"));
                profile.setCity(rs.getString("city"));
                profile.setState(rs.getString("state"));
                profile.setPostalCode(rs.getString("postal_code"));
                profile.setGovtIdType(rs.getString("govt_id_type"));
                profile.setGovtIdValue(rs.getString("govt_id_value"));
                profile.setKycStatus(rs.getString("kyc_status"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return profile;
    }

    public boolean updateProfile(CustomerProfile profile) {
        boolean updated = false;
        String sqlUsers = "UPDATE users SET email=?, phone=? WHERE user_id=?";
        String sqlCust  = "UPDATE customers SET first_name=?, last_name=?, dob=?, " +
                          "address_line1=?, address_line2=?, city=?, state=?, postal_code=? " +
                          "WHERE customer_id=?";
        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement ps1 = conn.prepareStatement(sqlUsers);
                 PreparedStatement ps2 = conn.prepareStatement(sqlCust)) {

                ps1.setString(1, profile.getEmail());
                ps1.setString(2, profile.getPhone());
                ps1.setInt(3, profile.getUserId());
                ps1.executeUpdate();

                ps2.setString(1, profile.getFirstName());
                ps2.setString(2, profile.getLastName());
                ps2.setString(3, profile.getDob());
                ps2.setString(4, profile.getAddressLine1());
                ps2.setString(5, profile.getAddressLine2());
                ps2.setString(6, profile.getCity());
                ps2.setString(7, profile.getState());
                ps2.setString(8, profile.getPostalCode());
                ps2.setInt(9, profile.getUserId());
                ps2.executeUpdate();

                conn.commit();
                updated = true;
            } catch (Exception e) {
                conn.rollback();
                throw e;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return updated;
    }
    
    public boolean changePassword(int userId, String oldPassword, String newPassword) {
        String checkSql = "SELECT password FROM users WHERE user_id=?";
        String updateSql = "UPDATE users SET password=? WHERE user_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps1 = conn.prepareStatement(checkSql)) {
            
            ps1.setInt(1, userId);
            ResultSet rs = ps1.executeQuery();
            if (rs.next()) {
                String currentPwd = rs.getString("password");
                if (!currentPwd.equals(oldPassword)) {
                    return false; // old password mismatch
                }
            } else {
                return false; // user not found
            }

            try (PreparedStatement ps2 = conn.prepareStatement(updateSql)) {
                ps2.setString(1, newPassword);
                ps2.setInt(2, userId);
                return ps2.executeUpdate() > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean isKycApproved(int customerId) {
        String sql = "SELECT kyc_status FROM customers WHERE customer_id = ?";
        try (Connection conn = DBConnection.getConnection();
        		PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return "VERIFIED".equalsIgnoreCase(rs.getString("kyc_status"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


}
