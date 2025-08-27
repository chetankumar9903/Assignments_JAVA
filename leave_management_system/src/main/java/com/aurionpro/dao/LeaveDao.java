package com.aurionpro.dao;


import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.aurionpro.database.DBConnection;
import com.aurionpro.model.LeaveApplication;

public class LeaveDao {

    private Connection conn;

    public LeaveDao() {
        this.conn = DBConnection.getInstance().getConnection();
        if (this.conn == null) {
            throw new RuntimeException("Failed to connect to the database.");
        }
    }

   

    
   
    public boolean applyLeave(LeaveApplication leave) {
        String sql = "INSERT INTO leave_applications(employee_id, date_from, date_to, no_of_days, reason, type_id, override_reason, status, applied_on) "
                   + "VALUES(?,?,?,?,?,?,?,? ,NOW())";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, leave.getEmployeeId());
            ps.setDate(2, Date.valueOf(leave.getDateFrom()));
            ps.setDate(3, Date.valueOf(leave.getDateTo()));
            ps.setInt(4, leave.getNoOfDays());
            ps.setString(5, leave.getReason());
            ps.setInt(6, leave.getTypeId());
            ps.setString(7, leave.getOverrideReason());
            ps.setString(8, leave.getStatus());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    

    public List<LeaveApplication> getLeavesByEmployee(int employeeId) {
        List<LeaveApplication> leaves = new ArrayList<>();
        String sql = "SELECT l.*, t.type_name AS leaveType " +
                     "FROM leave_applications l " +
                     "LEFT JOIN leave_types t ON l.type_id = t.type_id " +
                     "WHERE l.employee_id = ? ORDER BY applied_on DESC";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, employeeId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                LeaveApplication leave = mapLeaveWithType(rs);
                leaves.add(leave);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return leaves;
    }

 
    public List<LeaveApplication> getAllLeaves() {
        List<LeaveApplication> list = new ArrayList<>();
        String sql = "SELECT l.*, u.username, u.dept, t.type_name AS leaveType " +
                     "FROM leave_applications l " +
                     "JOIN users u ON l.employee_id=u.id " +
                     "LEFT JOIN leave_types t ON l.type_id = t.type_id " +
                     "ORDER BY applied_on DESC";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                LeaveApplication leave = mapLeaveWithType(rs);
                leave.setEmployeeName(rs.getString("username"));
                leave.setDepartment(rs.getString("dept"));
                list.add(leave);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }



 
    public boolean updateLeaveStatus(int leaveId, String status) {
        String sql = "UPDATE leave_applications SET status=? WHERE leave_id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, leaveId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    
    
    public List<LeaveApplication> getLeavesByStatus(String status) {
        List<LeaveApplication> leaves = new ArrayList<>();
        String sql = "SELECT l.*, u.full_name, u.dept FROM leave_applications l JOIN users u ON l.employee_id = u.id WHERE l.status = ? ORDER BY applied_on DESC";
        try { 
        	PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, status);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                leaves.add(mapLeave(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return leaves;
    }

    public List<LeaveApplication> getSanctionedLeaves() {
        List<LeaveApplication> leaves = new ArrayList<>();
        String sql = "SELECT l.*, u.full_name, u.dept FROM leave_applications l JOIN users u ON l.employee_id = u.id WHERE l.status IN ('Approved','Rejected') ORDER BY applied_on DESC";
        try { PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                leaves.add(mapLeave(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return leaves;
    }

    

    private LeaveApplication mapLeaveWithType(ResultSet rs) throws SQLException {
        LeaveApplication leave = new LeaveApplication();
        leave.setLeaveId(rs.getInt("leave_id"));
        leave.setEmployeeId(rs.getInt("employee_id"));
        leave.setDateFrom(rs.getDate("date_from").toLocalDate());
        leave.setDateTo(rs.getDate("date_to").toLocalDate());
        leave.setNoOfDays(rs.getInt("no_of_days"));
        leave.setReason(rs.getString("reason"));
        leave.setStatus(rs.getString("status"));
        leave.setOverrideReason(rs.getString("override_reason"));
        leave.setTypeId(rs.getInt("type_id"));
        leave.setLeaveType(rs.getString("leaveType")); // NEW: for JSP display
        Timestamp ts = rs.getTimestamp("applied_on");
        if (ts != null) {
            leave.setAppliedOn(ts.toLocalDateTime());
        }
        return leave;
    }
    
    private LeaveApplication mapLeave(ResultSet rs) throws SQLException {
        LeaveApplication leave = new LeaveApplication();
        leave.setLeaveId(rs.getInt("leave_id"));
        leave.setEmployeeId(rs.getInt("employee_id"));
        leave.setEmployeeName(rs.getString("full_name"));
        leave.setDepartment(rs.getString("dept"));
        leave.setDateFrom(rs.getDate("date_from").toLocalDate());
        leave.setDateTo(rs.getDate("date_to").toLocalDate());
        leave.setNoOfDays(rs.getInt("no_of_days"));
        leave.setReason(rs.getString("reason"));
        leave.setStatus(rs.getString("status"));
        leave.setTypeId(rs.getInt("type_id"));
        leave.setOverrideReason(rs.getString("override_reason"));
        Timestamp ts = rs.getTimestamp("applied_on");
        if (ts != null) {
            leave.setAppliedOn(ts.toLocalDateTime());
        }
        return leave;
    }
    
 
    public boolean hasOverlappingLeave(int empId, LocalDate from, LocalDate to) {
        String sql = "SELECT COUNT(*) FROM leave_applications " +
                     "WHERE employee_id = ? " +
                     "AND status IN ('Pending', 'Approved') " +
                     "AND NOT (date_to < ? OR date_from > ?)";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, empId);
            ps.setDate(2, Date.valueOf(from)); // new.start
            ps.setDate(3, Date.valueOf(to));   // new.end
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    
    public boolean updateLeave(LeaveApplication leave) {
        String sql = "UPDATE leave_applications SET date_from=?, date_to=?, no_of_days=?, reason=?, type_id=? WHERE leave_id=? AND status='Pending'";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(leave.getDateFrom()));
            ps.setDate(2, Date.valueOf(leave.getDateTo()));
            ps.setInt(3, leave.getNoOfDays());
            ps.setString(4, leave.getReason());
            ps.setInt(5, leave.getTypeId());
            ps.setInt(6, leave.getLeaveId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteLeave(int leaveId, int empId) {
        String sql = "DELETE FROM leave_applications WHERE leave_id=? AND employee_id=? AND status='Pending'";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, leaveId);
            ps.setInt(2, empId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public LeaveApplication getLeaveById(int leaveId) {
        String sql = "SELECT * FROM leave_applications WHERE leave_id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, leaveId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                LeaveApplication leave = new LeaveApplication();
                leave.setLeaveId(rs.getInt("leave_id"));
                leave.setEmployeeId(rs.getInt("employee_id"));
                leave.setDateFrom(rs.getDate("date_from").toLocalDate());
                leave.setDateTo(rs.getDate("date_to").toLocalDate());
                leave.setNoOfDays(rs.getInt("no_of_days"));
                leave.setReason(rs.getString("reason"));
                leave.setStatus(rs.getString("status"));
                leave.setOverrideReason(rs.getString("override_reason"));
                leave.setTypeId(rs.getInt("type_id"));
                return leave;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }



}