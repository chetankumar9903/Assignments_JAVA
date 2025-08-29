package com.aurionpro.dao;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.aurionpro.database.DBConnection;
import com.aurionpro.model.TransferStatus;

public class TransferDAO {
	/**
     * Calls stored procedure to transfer funds.
     * Returns map with keys: "code" (int), "message" (String)
     */
	
	/** single tranfer
    public TransferResult transfer(int fromAccountId, String toAccountNumber, double amount, String narration) throws SQLException {
        String sql = "{CALL sp_customer_transfer(?, ?, ?, ?, ?, ?)}";
        try (Connection con = DBConnection.getConnection();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, fromAccountId);
            cs.setString(2, toAccountNumber);
            cs.setBigDecimal(3, java.math.BigDecimal.valueOf(amount));
            cs.setString(4, narration);

            cs.registerOutParameter(5, Types.INTEGER); // result_code
            cs.registerOutParameter(6, Types.VARCHAR); // message

            cs.execute();

            int code = cs.getInt(5);
            String message = cs.getString(6);

            return new TransferResult(code, message);
        }
    }

    public static class TransferResult {
        private int code;
        private String message;

        public TransferResult(int code, String message) {
            this.code = code;
            this.message = message;
        }

        public int getCode() { return code; }
        public String getMessage() { return message; }
    }
    
    
    
    **/
	
	 public TransferResult internalTransfer(int fromAccountId, int toAccountId, double amount, int userId, String narration) throws SQLException {
	        String sql = "{CALL sp_transfer_internal(?, ?, ?, ?, ?)}";
	        try (Connection con = DBConnection.getConnection();
	             CallableStatement cs = con.prepareCall(sql)) {

	            cs.setInt(1, fromAccountId);
	            cs.setInt(2, toAccountId);
	            cs.setBigDecimal(3, java.math.BigDecimal.valueOf(amount));
	            cs.setInt(4, userId);
	            cs.setString(5, narration);

	            cs.execute();
	            return new TransferResult(0, "Internal transfer successful");
	        } catch (SQLException ex) {
	            return new TransferResult(-1, ex.getMessage());
	        }
	    }

	    public TransferResult externalTransfer(int fromAccountId, String toAcct, String toIfsc, double amount, int userId, String narration) throws SQLException {
	        String sql = "{CALL sp_transfer_external(?, ?, ?, ?, ?, ?)}";
	        try (Connection con = DBConnection.getConnection();
	             CallableStatement cs = con.prepareCall(sql)) {

	            cs.setInt(1, fromAccountId);
	            cs.setString(2, toAcct);
	            cs.setString(3, toIfsc);
	            cs.setBigDecimal(4, java.math.BigDecimal.valueOf(amount));
	            cs.setInt(5, userId);
	            cs.setString(6, narration);

	            cs.execute();
	            return new TransferResult(0, "External transfer successful");
	        } catch (SQLException ex) {
	            return new TransferResult(-1, ex.getMessage());
	        }
	    }

	    public static class TransferResult {
	        private int code;
	        private String message;

	        public TransferResult(int code, String message) {
	            this.code = code;
	            this.message = message;
	        }

	        public int getCode() { return code; }
	        public String getMessage() { return message; }
	    }
	    
	    /**
	    public boolean transfer(int userId, int beneficiaryId, double amount, String description) {
	        Connection conn = null;
	        PreparedStatement pstmt = null;
	        ResultSet rs = null;
	        boolean success = false;

	        try {
	            conn = DBConnection.getConnection();
	            conn.setAutoCommit(false);

	            // 1. Get sender account
	            String sqlSender = "SELECT account_id, balance FROM accounts WHERE user_id=? AND status='active' FOR UPDATE";
	            pstmt = conn.prepareStatement(sqlSender);
	            pstmt.setInt(1, userId);
	            rs = pstmt.executeQuery();

	            if (!rs.next()) {
	                conn.rollback();
	                return false;
	            }

	            int senderAccId = rs.getInt("account_id");
	            double senderBal = rs.getDouble("balance");

	            rs.close();
	            pstmt.close();

	            // 2. Get beneficiary account
	            String sqlBen = "SELECT b.beneficiary_account_no, a.account_id " +
	                            "FROM beneficiaries b JOIN accounts a ON b.beneficiary_account_no=a.account_no " +
	                            "WHERE b.beneficiary_id=? AND b.user_id=? AND a.status='active' FOR UPDATE";
	            pstmt = conn.prepareStatement(sqlBen);
	            pstmt.setInt(1, beneficiaryId);
	            pstmt.setInt(2, userId);
	            rs = pstmt.executeQuery();

	            if (!rs.next()) {
	                conn.rollback();
	                return false;
	            }

	            int receiverAccId = rs.getInt("account_id");
	            rs.close();
	            pstmt.close();

	            // 3. Check balance
	            if (senderBal < amount) {
	                conn.rollback();
	                return false;
	            }

	            // 4. Deduct from sender
	            String debitSQL = "UPDATE accounts SET balance=balance-? WHERE account_id=?";
	            pstmt = conn.prepareStatement(debitSQL);
	            pstmt.setDouble(1, amount);
	            pstmt.setInt(2, senderAccId);
	            pstmt.executeUpdate();
	            pstmt.close();

	            // 5. Credit to receiver
	            String creditSQL = "UPDATE accounts SET balance=balance+? WHERE account_id=?";
	            pstmt = conn.prepareStatement(creditSQL);
	            pstmt.setDouble(1, amount);
	            pstmt.setInt(2, receiverAccId);
	            pstmt.executeUpdate();
	            pstmt.close();

	            // 6. Insert into transactions
	            String txnSQL = "INSERT INTO transactions (from_account_id, to_account_id, amount, description) VALUES (?,?,?,?)";
	            pstmt = conn.prepareStatement(txnSQL);
	            pstmt.setInt(1, senderAccId);
	            pstmt.setInt(2, receiverAccId);
	            pstmt.setDouble(3, amount);
	            pstmt.setString(4, description);
	            pstmt.executeUpdate();

	            conn.commit();
	            success = true;

	        } catch (Exception e) {
	            e.printStackTrace();
	            try { conn.rollback(); } catch (Exception ignored) {}
	        } finally {
	            try { if (rs != null) rs.close(); } catch (Exception ignored) {}
	            try { if (pstmt != null) pstmt.close(); } catch (Exception ignored) {}
	            try { if (conn != null) conn.setAutoCommit(true); conn.close(); } catch (Exception ignored) {}
	        }
	        return success;
	    }
**/
	    
	    public TransferStatus transfer(int customerId, int beneficiaryId, double amount, String description) {
	        Connection conn = null;
	        PreparedStatement pstmt = null;
	        ResultSet rs = null;
	        TransferStatus status = TransferStatus.FAILED;

	        try {
	            conn = DBConnection.getConnection();
	            conn.setAutoCommit(false);

	            // 1. Get sender account
	            String sqlSender = "SELECT account_id, balance FROM accounts " +
	                               "WHERE customer_id=? AND status='ACTIVE' FOR UPDATE";
	            pstmt = conn.prepareStatement(sqlSender);
	            pstmt.setInt(1, customerId);
	            rs = pstmt.executeQuery();

	            if (!rs.next()) {
	                System.out.println("❌ No ACTIVE account found for customerId=" + customerId);
	                conn.rollback();
	                return TransferStatus.NO_ACCOUNT;

	            }

	            int senderAccId = rs.getInt("account_id");
	            double senderBal = rs.getDouble("balance");
	            System.out.println("✅ Sender Account: " + senderAccId + " | Balance=" + senderBal);

	            rs.close();
	            pstmt.close();

	            // 2. Get beneficiary info
	            String sqlBen = "SELECT b.is_internal, b.beneficiary_account_id, b.beneficiary_account_number, b.beneficiary_ifsc_code " +
	                            "FROM beneficiaries b " +
	                            "WHERE b.beneficiary_id=? AND b.owner_account_id=? FOR UPDATE";
	            pstmt = conn.prepareStatement(sqlBen);
	            pstmt.setInt(1, beneficiaryId);
	            pstmt.setInt(2, senderAccId);
	            rs = pstmt.executeQuery();

	            if (!rs.next()) {
	                System.out.println("❌ No beneficiary found with beneficiaryId=" + beneficiaryId +
	                                   " for ownerAccountId=" + senderAccId);
	                conn.rollback();
	                return TransferStatus.NO_BENEFICIARY;

	            }

	            boolean isInternal = rs.getBoolean("is_internal");
	            Integer receiverAccId = rs.getInt("beneficiary_account_id");
	            String externalAccNo = rs.getString("beneficiary_account_number");
	            String externalIfsc  = rs.getString("beneficiary_ifsc_code");

	            System.out.println("✅ Beneficiary found | internal=" + isInternal +
	                               " | receiverAccId=" + receiverAccId +
	                               " | externalAccNo=" + externalAccNo +
	                               " | IFSC=" + externalIfsc);

	            rs.close();
	            pstmt.close();

	            // 3. Check balance
	            if (senderBal < amount) {
	                System.out.println("❌ Insufficient funds: senderBal=" + senderBal + " < " + amount);
	                conn.rollback();
	                return TransferStatus.INSUFFICIENT_FUNDS;
	            }
	            if(amount<=0) {
	            	System.out.println("zero or Negative cannot be credited");
	            	conn.rollback();
	            	return TransferStatus.NEGATIVE;
	            }

	            // 4. Perform transfer
	            if (isInternal) {
	                // --- INTERNAL ---
	                if (receiverAccId == null) {
	                    System.out.println("❌ Internal beneficiary missing beneficiary_account_id");
	                    conn.rollback();
	                    return TransferStatus.INTERNAL ;
	                }

	                // Debit sender
	                String debitSQL = "UPDATE accounts SET balance=balance-? WHERE account_id=?";
	                pstmt = conn.prepareStatement(debitSQL);
	                pstmt.setDouble(1, amount);
	                pstmt.setInt(2, senderAccId);
	                pstmt.executeUpdate();
	                pstmt.close();

	                // Credit receiver
	                String creditSQL = "UPDATE accounts SET balance=balance+? WHERE account_id=?";
	                pstmt = conn.prepareStatement(creditSQL);
	                pstmt.setDouble(1, amount);
	                pstmt.setInt(2, receiverAccId);
	                pstmt.executeUpdate();
	                pstmt.close();

	                // Insert transaction
	                String txnSQL = "INSERT INTO transactions (from_account_id, to_account_id, amount, type, status, description, initiated_by, posted_at) " +
	                                "VALUES (?,?,?,?,?,?,?,NOW())";
	                pstmt = conn.prepareStatement(txnSQL);
	                pstmt.setInt(1, senderAccId);
	                pstmt.setInt(2, receiverAccId);
	                pstmt.setDouble(3, amount);
	                pstmt.setString(4, "TRANSFER");
	                pstmt.setString(5, "SUCCESS");
	                pstmt.setString(6, description);
	                pstmt.setInt(7, customerId); // who initiated
	                pstmt.executeUpdate();
	                pstmt.close();

	                System.out.println("✅ Internal transfer completed: " + amount);
	                status = TransferStatus.SUCCESS;


	            } else {
	                // --- EXTERNAL ---
	                if (externalAccNo == null || externalIfsc == null) {
	                    System.out.println("❌ External beneficiary missing accountNo/IFSC");
	                    conn.rollback();
	                    return TransferStatus.EXTERNAL;
	                }

	                // Debit sender
	                String debitSQL = "UPDATE accounts SET balance=balance-? WHERE account_id=?";
	                pstmt = conn.prepareStatement(debitSQL);
	                pstmt.setDouble(1, amount);
	                pstmt.setInt(2, senderAccId);
	                pstmt.executeUpdate();
	                pstmt.close();
	                
	                // Credit receiver external
	                String creditSQL = "UPDATE accounts SET balance=balance+? WHERE account_number=?";
	                pstmt = conn.prepareStatement(creditSQL);
	                pstmt.setDouble(1, amount);
	                pstmt.setString(2, externalAccNo);
	                pstmt.executeUpdate();
	                pstmt.close();

	                // Insert transaction (external only)
	                String txnSQL = "INSERT INTO transactions (from_account_id, to_external_account_number, to_external_ifsc, amount, type, status, description, initiated_by, posted_at) " +
	                                "VALUES (?,?,?,?,?,?,?,?,NOW())";
	                pstmt = conn.prepareStatement(txnSQL);
	                pstmt.setInt(1, senderAccId);
	                pstmt.setString(2, externalAccNo);
	                pstmt.setString(3, externalIfsc);
	                pstmt.setDouble(4, amount);
	                pstmt.setString(5, "TRANSFER");
	                pstmt.setString(6, "SUCCESS");
	                pstmt.setString(7, description);
	                pstmt.setInt(8, customerId);
	                pstmt.executeUpdate();
	                pstmt.close();

	                System.out.println("✅ External transfer recorded: " + amount + " to " + externalAccNo + "@" + externalIfsc);
	            }

	            conn.commit();
	            status = TransferStatus.SUCCESS;


	        } catch (Exception e) {
	            e.printStackTrace();
	            try { conn.rollback(); } catch (Exception ignored) {}
	        } finally {
	            try { if (rs != null) rs.close(); } catch (Exception ignored) {}
	            try { if (pstmt != null) pstmt.close(); } catch (Exception ignored) {}
	            try { if (conn != null) conn.setAutoCommit(true); conn.close(); } catch (Exception ignored) {}
	        }
	        return status;
	    }

	    
	    
	    /** with procedures
	     public boolean transfer(int customerId, int beneficiaryId, double amount, String description) {
    Connection conn = null;
    CallableStatement cs = null;
    ResultSet rs = null;
    boolean success = false;

    try {
        conn = DBConnection.getConnection();

        // 1. Get sender account_id (since stored proc needs it)
        String sqlSender = "SELECT account_id FROM accounts WHERE customer_id=? AND status='ACTIVE'";
        try (PreparedStatement ps = conn.prepareStatement(sqlSender)) {
            ps.setInt(1, customerId);
            rs = ps.executeQuery();
            if (!rs.next()) {
                System.out.println("❌ No ACTIVE account found for customerId=" + customerId);
                return false;
            }
            int senderAccId = rs.getInt("account_id");
            rs.close();

            // 2. Check if beneficiary is internal/external
            String sqlBen = "SELECT is_internal, beneficiary_account_id, beneficiary_account_number, beneficiary_ifsc_code " +
                            "FROM beneficiaries WHERE beneficiary_id=? AND owner_account_id=?";
            try (PreparedStatement ps2 = conn.prepareStatement(sqlBen)) {
                ps2.setInt(1, beneficiaryId);
                ps2.setInt(2, senderAccId);
                rs = ps2.executeQuery();

                if (!rs.next()) {
                    System.out.println("❌ Beneficiary not found for ownerAccId=" + senderAccId);
                    return false;
                }

                boolean isInternal = rs.getBoolean("is_internal");
                Integer receiverAccId = rs.getInt("beneficiary_account_id");
                String externalAccNo = rs.getString("beneficiary_account_number");
                String externalIfsc  = rs.getString("beneficiary_ifsc_code");
                rs.close();

                if (isInternal) {
                    // --- Call Internal Transfer SP ---
                    cs = conn.prepareCall("{CALL sp_transfer_internal(?,?,?,?)}");
                    cs.setInt(1, senderAccId);
                    cs.setInt(2, receiverAccId);
                    cs.setDouble(3, amount);
                    cs.setString(4, description);
                    cs.execute();
                    success = true;
                    System.out.println("✅ Internal transfer done using SP");

                } else {
                    // --- Call External Transfer SP ---
                    cs = conn.prepareCall("{CALL sp_transfer_external(?,?,?,?)}");
                    cs.setInt(1, senderAccId);
                    cs.setString(2, externalAccNo);
                    cs.setString(3, externalIfsc);
                    cs.setDouble(4, amount);
                    cs.execute();
                    success = true;
                    System.out.println("✅ External transfer done using SP");
                }
            }
        }

    } catch (Exception e) {
        e.printStackTrace();
        success = false;
    } finally {
        try { if (rs != null) rs.close(); } catch (Exception ignored) {}
        try { if (cs != null) cs.close(); } catch (Exception ignored) {}
        try { if (conn != null) conn.close(); } catch (Exception ignored) {}
    }
    return success;
}



//    with stataus
public TransferStatus transfer(int customerId, int beneficiaryId, double amount, String description) {
    Connection conn = null;
    CallableStatement cs = null;
    ResultSet rs = null;

    try {
        conn = DBUtil.getConnection();

        // 1. Find sender account
        String accSql = "SELECT account_id FROM accounts WHERE customer_id=? AND status='ACTIVE'";
        try (PreparedStatement ps = conn.prepareStatement(accSql)) {
            ps.setInt(1, customerId);
            rs = ps.executeQuery();
            if (!rs.next()) {
                return TransferStatus.NO_ACCOUNT;
            }
        }
        int senderAccId = rs.getInt("account_id");

        // 2. Find beneficiary
        String benSql = "SELECT is_internal, beneficiary_account_id FROM beneficiaries WHERE beneficiary_id=? AND owner_account_id=?";
        try (PreparedStatement ps = conn.prepareStatement(benSql)) {
            ps.setInt(1, beneficiaryId);
            ps.setInt(2, senderAccId);
            rs = ps.executeQuery();
            if (!rs.next()) {
                return TransferStatus.NO_BENEFICIARY;
            }

            boolean isInternal = rs.getBoolean("is_internal");
            int receiverAccId = rs.getInt("beneficiary_account_id");

            // 3. Call the correct stored procedure
            if (isInternal) {
                cs = conn.prepareCall("{CALL sp_transfer_internal(?, ?, ?, ?, ?)}");
                cs.setInt(1, senderAccId);
                cs.setInt(2, receiverAccId);
                cs.setDouble(3, amount);
                cs.setString(4, description);
                cs.registerOutParameter(5, Types.VARCHAR);
            } else {
                cs = conn.prepareCall("{CALL sp_transfer_external(?, ?, ?, ?, ?)}");
                cs.setInt(1, senderAccId);
                cs.setInt(2, beneficiaryId);
                cs.setDouble(3, amount);
                cs.setString(4, description);
                cs.registerOutParameter(5, Types.VARCHAR);
            }

            cs.execute();
            String result = cs.getString(5);

            // 4. Map result string to enum
            switch (result) {
                case "SUCCESS": return TransferStatus.SUCCESS;
                case "INSUFFICIENT_FUNDS": return TransferStatus.INSUFFICIENT_FUNDS;
                case "NO_ACCOUNT": return TransferStatus.NO_ACCOUNT;
                case "NO_BENEFICIARY": return TransferStatus.NO_BENEFICIARY;
                default: return TransferStatus.FAILED;
            }
        }

    } catch (Exception e) {
        e.printStackTrace();
        return TransferStatus.ERROR;
    } finally {
        try { if (rs != null) rs.close(); } catch (Exception ignored) {}
        try { if (cs != null) cs.close(); } catch (Exception ignored) {}
        try { if (conn != null) conn.close(); } catch (Exception ignored) {}
    }
}

	     
	     **/
	    
	   

}
