package com.aurionpro.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.aurionpro.database.DBConnection;
import com.aurionpro.model.PassbookEntry;

public class PassbookDAO {
	private Connection connection;

	public PassbookDAO() {
		this.connection = DBConnection.getConnection();
		if (this.connection == null) {
			throw new RuntimeException("Failed to connect to the database.");
		}
	}

//	public List<PassbookEntry> listPassbook(int accountId, String type, String status, String fromDate, String toDate,
//			Integer limit, Integer offset){
//		List<PassbookEntry> list = new ArrayList<>();
//
//		StringBuilder sql = new StringBuilder();
//		sql.append("SELECT txn_id, txn_date, type, status, narration, debit, credit, account_id ")
//				.append("FROM v_passbook WHERE account_id = ? ");
//
//		if (type != null && !type.isBlank())
//			sql.append("AND type = ? ");
//		if (status != null && !status.isBlank())
//			sql.append("AND status = ? ");
//		if (fromDate != null && !fromDate.isBlank())
//			sql.append("AND txn_date >= ? ");
//		if (toDate != null && !toDate.isBlank())
//			sql.append("AND txn_date <= ? ");
//		sql.append("ORDER BY txn_date DESC ");
//
//		if (limit != null && offset != null)
//			sql.append("LIMIT ? OFFSET ? ");
//
//		try {
//				PreparedStatement ps = connection.prepareStatement(sql.toString());
//			int i = 1;
//			ps.setInt(i++, accountId);
//			if (type != null && !type.isBlank())
//				ps.setString(i++, type);
//			if (status != null && !status.isBlank())
//				ps.setString(i++, status);
//			if (fromDate != null && !fromDate.isBlank())
//				ps.setString(i++, fromDate + " 00:00:00");
//			if (toDate != null && !toDate.isBlank())
//				ps.setString(i++, toDate + " 23:59:59");
//			if (limit != null && offset != null) {
//				ps.setInt(i++, limit);
//				ps.setInt(i++, offset);
//			}
//
//			try (ResultSet rs = ps.executeQuery()) {
//				while (rs.next()) {
//					PassbookEntry p = new PassbookEntry();
//					p.setTxnId(rs.getInt("txn_id"));
//					p.setTxnDate(rs.getTimestamp("txn_date"));
//					p.setType(rs.getString("type"));
//					p.setStatus(rs.getString("status"));
//					p.setNarration(rs.getString("narration"));
//					p.setDebit(rs.getBigDecimal("debit"));
//					p.setCredit(rs.getBigDecimal("credit"));
//					p.setAccountId(rs.getInt("account_id"));
//					list.add(p);
//				}
//			}
//		}catch (SQLException e) {
//            e.printStackTrace();
//        }
//        
//		return list;
//	}
//	
	
	public List<PassbookEntry> listPassbook(
	        int accountId, String type, String status,
	        String fromDate, String toDate,
	        Integer limit, Integer offset,
	        HttpServletRequest request) {   // add request if you want to set hasNext directly

	    List<PassbookEntry> list = new ArrayList<>();

	    StringBuilder sql = new StringBuilder();
	    sql.append("SELECT txn_id, txn_date, type, status, narration, debit, credit, account_id, ")
	    .append("from_account_id, to_account_id, to_external_account_number, to_external_ifsc ")
	    .append("FROM v_passbook WHERE account_id = ? ");

	    if (type != null && !type.isBlank())
	        sql.append("AND type = ? ");
	    if (status != null && !status.isBlank())
	        sql.append("AND status = ? ");
	    if (fromDate != null && !fromDate.isBlank())
	        sql.append("AND txn_date >= ? ");
	    if (toDate != null && !toDate.isBlank())
	        sql.append("AND txn_date <= ? ");
	    sql.append("ORDER BY txn_date DESC ");

	    if (limit != null && offset != null)
	        sql.append("LIMIT ? OFFSET ? ");

	    try {
	        PreparedStatement ps = connection.prepareStatement(sql.toString());
	        int i = 1;
	        ps.setInt(i++, accountId);
	        if (type != null && !type.isBlank())
	            ps.setString(i++, type);
	        if (status != null && !status.isBlank())
	            ps.setString(i++, status);
	        if (fromDate != null && !fromDate.isBlank())
	            ps.setString(i++, fromDate + " 00:00:00");
	        if (toDate != null && !toDate.isBlank())
	            ps.setString(i++, toDate + " 23:59:59");
	        if (limit != null && offset != null) {
	            ps.setInt(i++, limit + 1);  // ask for 1 extra row
	            ps.setInt(i++, offset);
	        }

	        try (ResultSet rs = ps.executeQuery()) {
	            while (rs.next()) {
	                PassbookEntry p = new PassbookEntry();
	                p.setTxnId(rs.getInt("txn_id"));
	                p.setTxnDate(rs.getTimestamp("txn_date"));
	                p.setType(rs.getString("type"));
	                p.setStatus(rs.getString("status"));
	                p.setNarration(rs.getString("narration"));
	                p.setDebit(rs.getBigDecimal("debit"));
	                p.setCredit(rs.getBigDecimal("credit"));
	                p.setAccountId(rs.getInt("account_id"));
	                p.setFromAccountId(rs.getInt("from_account_id"));
	                if (rs.wasNull()) p.setFromAccountId(null);

	                p.setToAccountId(rs.getInt("to_account_id"));
	                if (rs.wasNull()) p.setToAccountId(null);

	                p.setToExternalAccount(rs.getString("to_external_account_number"));
	                p.setToExternalIfsc(rs.getString("to_external_ifsc"));

	                list.add(p);
	            }
	        }

	        // ✅ detect hasNext
	        boolean hasNext = false;
	        if (limit != null && list.size() > limit) {
	            hasNext = true;
	            list = list.subList(0, limit); // trim the extra row
	        }

	        // pass flag to JSP
	        request.setAttribute("hasNext", hasNext);

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return list;
	}

	
	/**
	public List<PassbookEntry> getPassbook(
            int accountId, Date startDate, Date endDate,
            String type, String status,
            Double minAmount, Double maxAmount,
            String search) {

        List<PassbookEntry> entries = new ArrayList<>();

        String sql = "{CALL sp_get_passbook(?,?,?,?,?,?,?,?)}";
        try {
             CallableStatement cs = connection.prepareCall(sql);

            cs.setInt(1, accountId);
            cs.setDate(2, startDate != null ? new java.sql.Date(startDate.getTime()) : null);
            cs.setDate(3, endDate != null ? new java.sql.Date(endDate.getTime()) : null);
            cs.setString(4, type);
            cs.setString(5, status);
            if (minAmount != null) cs.setBigDecimal(6, new java.math.BigDecimal(minAmount));
            else cs.setNull(6, Types.DECIMAL);
            if (maxAmount != null) cs.setBigDecimal(7, new java.math.BigDecimal(maxAmount));
            else cs.setNull(7, Types.DECIMAL);
            cs.setString(8, search);

            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    PassbookEntry e = new PassbookEntry();
                    e.setTxnId(rs.getInt("txn_id"));
                    e.setTxnDate(rs.getTimestamp("txn_date"));
                    e.setType(rs.getString("type"));
                    e.setStatus(rs.getString("status"));
                    e.setNarration(rs.getString("narration"));
                    e.setDebit(rs.getBigDecimal("debit"));
                    e.setCredit(rs.getBigDecimal("credit"));
                    entries.add(e);
                }
            }
        }catch(SQLException e) {
        	e.printStackTrace();
        }
        return entries;
    }
    **/

}
