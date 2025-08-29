package com.aurionpro.dao;

import java.util.ArrayList;


import java.sql.*;
import java.util.*;

import com.aurionpro.database.DBConnection;
import com.aurionpro.model.Beneficiary;

public class BeneficiaryDAO {
	public boolean addBeneficiary(Beneficiary b) throws Exception {
        Connection con = DBConnection.getConnection();

        if (b.isInternal()) {
            PreparedStatement check = con.prepareStatement(
                "SELECT status FROM accounts WHERE account_id=?");
            check.setInt(1, b.getBeneficiaryAccountId());
            ResultSet rs = check.executeQuery();
            if (!rs.next() || !"ACTIVE".equalsIgnoreCase(rs.getString("status"))) {
                return false; // account not active
            }
        }

        PreparedStatement ps = con.prepareStatement(
            "INSERT INTO beneficiaries(owner_account_id, nickname, beneficiary_name, " +
            "beneficiary_account_id, beneficiary_account_number, beneficiary_ifsc_code, is_internal) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?)");
        ps.setInt(1, b.getOwnerAccountId());
        ps.setString(2, b.getNickname());
        ps.setString(3, b.getBeneficiaryName());
        if (b.isInternal()) {
            ps.setInt(4, b.getBeneficiaryAccountId());
            ps.setNull(5, Types.VARCHAR);
            ps.setNull(6, Types.VARCHAR);
        } else {
            ps.setNull(4, Types.INTEGER);
            ps.setString(5, b.getBeneficiaryAccountNumber());
            ps.setString(6, b.getBeneficiaryIfscCode());
        }
        ps.setBoolean(7, b.isInternal());

        return ps.executeUpdate() > 0;
    }

    public List<Beneficiary> getBeneficiariesByOwner(int ownerAccountId) throws Exception {
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement("SELECT * FROM beneficiaries WHERE owner_account_id=?");
        ps.setInt(1, ownerAccountId);
        ResultSet rs = ps.executeQuery();

        List<Beneficiary> list = new ArrayList<>();
        while (rs.next()) {
            Beneficiary b = new Beneficiary();
            b.setBeneficiaryId(rs.getInt("beneficiary_id"));
            b.setOwnerAccountId(rs.getInt("owner_account_id"));
            b.setNickname(rs.getString("nickname"));
            b.setBeneficiaryName(rs.getString("beneficiary_name"));
            b.setBeneficiaryAccountId((Integer)rs.getObject("beneficiary_account_id"));
            b.setBeneficiaryAccountNumber(rs.getString("beneficiary_account_number"));
            b.setBeneficiaryIfscCode(rs.getString("beneficiary_ifsc_code"));
            b.setInternal(rs.getBoolean("is_internal"));
            b.setCreatedAt(rs.getTimestamp("created_at"));
            list.add(b);
        }
        return list;
    }

    public boolean deleteBeneficiary(int beneficiaryId, int ownerAccountId) throws Exception {
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(
            "DELETE FROM beneficiaries WHERE beneficiary_id=? AND owner_account_id=?");
        ps.setInt(1, beneficiaryId);
        ps.setInt(2, ownerAccountId);
        return ps.executeUpdate() > 0;
    }
    public int getAccountIdByCustomer(int customerId){
        String sql = "SELECT account_id FROM accounts WHERE customer_id = ? LIMIT 1";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, customerId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("account_id");
                }
            }
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return -1; // not found
    }


}
