package com.aurionpro.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.aurionpro.database.DBConnection;
import com.aurionpro.model.Account;

public class AccountDAO {
	
	private Connection connection;

	public AccountDAO() {
		this.connection = DBConnection.getConnection();
		if (this.connection == null) {
			throw new RuntimeException("Failed to connect to the database.");
		}
	}
	

    public List<Account> findAccountsByCustomer(int customerId){
        String sql = "SELECT * FROM accounts WHERE customer_id = ? ORDER BY created_at DESC";
        List<Account> list = new ArrayList<>();
        try {
             PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Account a = new Account();
                    a.setAccountId(rs.getInt("account_id"));
                    a.setCustomerId(rs.getInt("customer_id"));
                    a.setAccountNumber(rs.getString("account_number"));
                    a.setAccountType(rs.getString("account_type"));
                    a.setIfscCode(rs.getString("ifsc_code"));
                    a.setBalance(rs.getBigDecimal("balance"));
                    a.setStatus(rs.getString("status"));
                    a.setOpenedOn(rs.getDate("opened_on"));
                    a.setCreatedAt(rs.getTimestamp("created_at"));
                    list.add(a);
                }
            
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Account findByIdForCustomer(int accountId, int customerId) throws SQLException {
        String sql = "SELECT * FROM accounts WHERE account_id = ? AND customer_id = ?";
        try {
             PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, accountId);
            ps.setInt(2, customerId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Account a = new Account();
                    a.setAccountId(rs.getInt("account_id"));
                    a.setCustomerId(rs.getInt("customer_id"));
                    a.setAccountNumber(rs.getString("account_number"));
                    a.setAccountType(rs.getString("account_type"));
                    a.setIfscCode(rs.getString("ifsc_code"));
                    a.setBalance(rs.getBigDecimal("balance"));
                    a.setStatus(rs.getString("status"));
                    a.setOpenedOn(rs.getDate("opened_on"));
                    a.setCreatedAt(rs.getTimestamp("created_at"));
                    return a;
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
