package com.aurionpro.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.aurionpro.database.DBConnection;

public class LeaveBalanceDAO {
    
    private Connection conn;

    public LeaveBalanceDAO() {
        this.conn = DBConnection.getInstance().getConnection();
        if (this.conn == null) {
            throw new RuntimeException("Failed to connect to the database.");
        }
    }
    
  
    public int getUsedLeaves(int empId, int typeId, String yearMonth) {
        String sql = "SELECT used FROM leave_balances WHERE employee_id=? AND type_id=? AND `year_month`=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, empId);
            ps.setInt(2, typeId);
            ps.setString(3, yearMonth);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt("used");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

   
    public boolean updateUsedLeaves(int empId, int typeId, String yearMonth, int used) {
        String sql = "INSERT INTO leave_balances(employee_id, type_id, `year_month`, used) " +
                     "VALUES(?, ?, ?, ?) " +
                     "ON DUPLICATE KEY UPDATE used=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, empId);
            ps.setInt(2, typeId);
            ps.setString(3, yearMonth);
            ps.setInt(4, used);
            ps.setInt(5, used);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Map<Integer, Integer> getYearlyUsedLeaves(int empId, String year) {
        Map<Integer, Integer> yearlyLeaves = new HashMap<>();
        String sql = "SELECT type_id, SUM(used) AS total_used " +
                     "FROM leave_balances " +
                     "WHERE employee_id=? AND `year_month` LIKE ? " +
                     "GROUP BY type_id";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, empId);
            ps.setString(2, year + "%");  // e.g. "2025%"
            try (ResultSet rs = ps.executeQuery()) {
                while(rs.next()) {
                    yearlyLeaves.put(rs.getInt("type_id"), rs.getInt("total_used"));
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return yearlyLeaves;
    }

    
    
    public boolean decrementUsedLeaves(int empId, int typeId, String yearMonth, int decrementBy) {
        int currentUsed = getUsedLeaves(empId, typeId, yearMonth);
        int newUsed = Math.max(0, currentUsed - decrementBy);
        return updateUsedLeaves(empId, typeId, yearMonth, newUsed);
    }
    
    public boolean updateLeaveBalance(int empId, int typeId, int noOfDays){
        String query = "UPDATE leave_balances SET used = used + ? WHERE employee_id = ? AND type_d = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, noOfDays);
            ps.setInt(2, empId);
            ps.setInt(3, typeId);
            return ps.executeUpdate() > 0;
        }
        catch (SQLException e) { e.printStackTrace(); }
        return false;
    }
}
