package com.aurionpro.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.aurionpro.database.DBConnection;
import com.aurionpro.model.LeaveType;

public class LeaveTypeDAO {
	
	private Connection conn;

	public LeaveTypeDAO() {
	    this.conn = DBConnection.getInstance().getConnection();
	    if (this.conn == null) {
	        throw new RuntimeException("Failed to connect to the database.");
	    }
	}
	
	public List<LeaveType> getAllLeaveTypes() {
        List<LeaveType> list = new ArrayList<>();
        String sql = "SELECT * FROM leave_types";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while(rs.next()) {
                list.add(new LeaveType(rs.getInt("type_id"), rs.getString("type_name"), rs.getInt("monthly_limit")));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public LeaveType getLeaveTypeById(int typeId) {
        String sql = "SELECT * FROM leave_types WHERE type_id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, typeId);
            try (ResultSet rs = ps.executeQuery()) {
                if(rs.next()) {
                    return new LeaveType(rs.getInt("type_id"), rs.getString("type_name"), rs.getInt("monthly_limit"));
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

}
