package com.aurionpro.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.aurionpro.database.DBConnection;
public class AccountApplicationDAO {

    public List<Map<String, Object>> listPendingApplications(String q, String dateFrom, String dateTo, Integer limit,
                                                             Integer offset) {
        List<Map<String, Object>> list = new ArrayList<>();

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT a.application_id, a.user_id, u.username, a.account_type, a.initial_deposit, a.status, a.created_at ")
           .append("FROM account_applications a ")
           .append("JOIN users u ON u.user_id = a.user_id ")
           .append("WHERE a.status = 'SUBMITTED' ");

        if (q != null && !q.isBlank()) {
            sql.append("AND u.username LIKE ? ");
        }
        if (dateFrom != null && !dateFrom.isBlank()) {
            sql.append("AND a.created_at >= ? ");
        }
        if (dateTo != null && !dateTo.isBlank()) {
            sql.append("AND a.created_at <= ? ");
        }
        sql.append("ORDER BY a.created_at DESC ");

        if (limit != null && offset != null) {
            sql.append("LIMIT ? OFFSET ? ");
        }

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            int idx = 1;
            if (q != null && !q.isBlank()) ps.setString(idx++, "%" + q + "%");
            if (dateFrom != null && !dateFrom.isBlank()) ps.setString(idx++, dateFrom + " 00:00:00");
            if (dateTo != null && !dateTo.isBlank()) ps.setString(idx++, dateTo + " 23:59:59");
            if (limit != null && offset != null) {
                ps.setInt(idx++, limit);
                ps.setInt(idx, offset);
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("application_id", rs.getInt("application_id"));
                row.put("user_id", rs.getInt("user_id"));
                row.put("username", rs.getString("username"));
                row.put("account_type", rs.getString("account_type"));
                row.put("initial_deposit", rs.getBigDecimal("initial_deposit"));
                row.put("status", rs.getString("status"));
                row.put("created_at", rs.getTimestamp("created_at"));
                list.add(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public void approveApplication(int applicationId, int adminUserId) {
        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            try (CallableStatement cs = conn.prepareCall("{CALL sp_admin_approve_ap(?, ?)}")) {
                cs.setInt(1, applicationId);
                cs.setInt(2, adminUserId);
                cs.execute();
                conn.commit(); // commit only if success
            } catch (SQLException e) {
                conn.rollback(); // rollback if failed
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int countPendingApplications() {
        String sql = "SELECT COUNT(*) FROM account_applications WHERE status = 'SUBMITTED'";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) return rs.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // same pattern for rejectApplication() and createApplication()
    

	/**
	 * Reject an application: sets status='REJECTED', reviewed_by and reviewed_at,
	 * and store remarks.
	 */
	public void rejectApplication(int applicationId, int adminUserId, String remarks){
		String sql = "UPDATE account_applications SET status = 'REJECTED', reviewed_by = ?, reviewed_at = NOW(), remarks = ? WHERE application_id = ? AND status = 'SUBMITTED'";
		try ( Connection conn = DBConnection.getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql)){
		
			ps.setInt(1, adminUserId);
			ps.setString(2, remarks);
			ps.setInt(3, applicationId);
			int updated = ps.executeUpdate();
			if (updated == 0) {
				throw new SQLException("Application not found or not in SUBMITTED state.");
			}
		}catch (SQLException e){
			e.printStackTrace();
		}
	}


	 public int createApplication(int userId, String accountType, java.math.BigDecimal initialDeposit)  {
	        String sql = "INSERT INTO account_applications (user_id, account_type, initial_deposit, status) VALUES (?, ?, ?, 'SUBMITTED')";
	        try ( Connection conn = DBConnection.getConnection();
		             PreparedStatement ps = conn.prepareStatement(sql)){
	            ps.setInt(1, userId);
	            ps.setString(2, accountType);
	            ps.setBigDecimal(3, initialDeposit == null ? java.math.BigDecimal.ZERO : initialDeposit);
	            return ps.executeUpdate();
	        }catch (SQLException e){
				e.printStackTrace();
			}
	        return -1;
	    }
	 
	 
	 public Map<String,Object> getApplicationDetails(int applicationId) {
	        String sql = "SELECT aa.application_id, aa.account_type, aa.initial_deposit, aa.status, aa.remarks, aa.created_at, "
	                   + "c.first_name, c.last_name, c.dob, c.address_line1, c.address_line2, c.city, c.state, c.postal_code, "
	                   + "c.govt_id_type, c.govt_id_value, c.kyc_status, u.email, u.phone "
	                   + "FROM account_applications aa "
	                   + "JOIN users u ON u.user_id = aa.user_id "
	                   + "JOIN customers c ON c.customer_id = u.user_id "
	                   + "WHERE aa.application_id = ?";

	        try (Connection conn = DBConnection.getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql)) {
	            ps.setInt(1, applicationId);
	            ResultSet rs = ps.executeQuery();
	            if (rs.next()) {
	                Map<String,Object> map = new HashMap<>();
	                map.put("application_id", rs.getInt("application_id"));
	                map.put("account_type", rs.getString("account_type"));
	                map.put("initial_deposit", rs.getBigDecimal("initial_deposit"));
	                map.put("status", rs.getString("status"));
	                map.put("remarks", rs.getString("remarks"));
	                map.put("created_at", rs.getTimestamp("created_at"));

	                map.put("first_name", rs.getString("first_name"));
	                map.put("last_name", rs.getString("last_name"));
	                map.put("dob", rs.getDate("dob"));
	                map.put("address_line1", rs.getString("address_line1"));
	                map.put("address_line2", rs.getString("address_line2"));
	                map.put("city", rs.getString("city"));
	                map.put("state", rs.getString("state"));
	                map.put("postal_code", rs.getString("postal_code"));
	                map.put("govt_id_type", rs.getString("govt_id_type"));
	                map.put("govt_id_value", rs.getString("govt_id_value"));
	                map.put("kyc_status", rs.getString("kyc_status"));
	                map.put("email", rs.getString("email"));
	                map.put("phone", rs.getString("phone"));
	                return map;
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return null;
	    }
	 
//	 public List<Map<String, Object>> getApprovedApplications() {
//	        List<Map<String, Object>> approvedList = new ArrayList<>();
//
//	        String sql = "SELECT u.user_id, u.username, u.email, u.phone, " +
//	                "c.first_name, c.last_name, c.dob, c.address_line1, c.address_line2, c.city, c.state, c.postal_code, " +
//	                "c.govt_id_type, c.govt_id_value, c.kyc_status, a.account_id, a.account_number, a.account_type, a.balance, a.status AS account_status, " +
//	                "aa.application_id, aa.initial_deposit, aa.created_at AS app_created, aa.reviewed_by, aa.reviewed_at, aa.remarks " +
//	                "FROM users u " +
//	                "JOIN customers c ON c.customer_id = u.user_id " +
//	                "JOIN account_applications aa ON aa.user_id = u.user_id " +
//	                "JOIN accounts a ON a.customer_id = u.user_id " +
//	                "WHERE aa.status='APPROVED' " +
//	                "ORDER BY aa.reviewed_at DESC";
//
//	        try (Connection conn = DBConnection.getConnection();
//	        		PreparedStatement ps = conn.prepareStatement(sql);
//	             ResultSet rs = ps.executeQuery()) {
//
//	            while (rs.next()) {
//	                Map<String, Object> row = new HashMap<>();
//
//	                row.put("userId", rs.getInt("user_id"));
//	                row.put("username", rs.getString("username"));
//	                row.put("email", rs.getString("email"));
//	                row.put("phone", rs.getString("phone"));
//
//	                row.put("firstName", rs.getString("first_name"));
//	                row.put("lastName", rs.getString("last_name"));
//	                row.put("dob", rs.getDate("dob"));
//	                row.put("addressLine1", rs.getString("address_line1"));
//	                row.put("addressLine2", rs.getString("address_line2"));
//	                row.put("city", rs.getString("city"));
//	                row.put("state", rs.getString("state"));
//	                row.put("postalCode", rs.getString("postal_code"));
//	                row.put("govtIdType", rs.getString("govt_id_type"));
//	                row.put("govtIdValue", rs.getString("govt_id_value"));
//	                row.put("kycStatus", rs.getString("kyc_status"));
//
//	                row.put("accountId", rs.getInt("account_id"));
//	                row.put("accountNumber", rs.getString("account_number"));
//	                row.put("accountType", rs.getString("account_type"));
//	                row.put("balance", rs.getDouble("balance"));
//	                row.put("accountStatus", rs.getString("account_status"));
//
//	                row.put("applicationId", rs.getInt("application_id"));
//	                row.put("initialDeposit", rs.getDouble("initial_deposit"));
//	                row.put("appCreated", rs.getTimestamp("app_created"));
//	                row.put("reviewedBy", rs.getString("reviewed_by"));
//	                row.put("reviewedAt", rs.getTimestamp("reviewed_at"));
//	                row.put("remarks", rs.getString("remarks"));
//
//	                approvedList.add(row);
//	            }
//	        } catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//	        return approvedList;
//	    }
	 
	 
	 // with more fields with fixed approve status
	 public List<Map<String, Object>> getApprovedApplications() {
		    List<Map<String, Object>> approvedList = new ArrayList<>();

		    String sql = "SELECT " +
		            "u.user_id, u.username, u.email, u.phone, " +
		            "c.first_name, c.last_name, c.dob, c.address_line1, c.address_line2, c.city, c.state, c.postal_code, " +
		            "c.govt_id_type, c.govt_id_value, c.kyc_status, " +
		            "a.account_id, a.account_number, a.account_type, a.balance, a.status AS account_status, a.ifsc_code, " +
		            "b.bank_name, b.branch_name, b.city AS branch_city, " +
		            "aa.application_id, aa.initial_deposit, aa.created_at AS app_created, " +
		            "aa.reviewed_by, r.username AS reviewed_by_name, " +
		            "aa.reviewed_at, aa.remarks " +
		            "FROM users u " +
		            "JOIN customers c ON c.customer_id = u.user_id " +
		            "LEFT JOIN accounts a ON a.customer_id = c.customer_id " +
		            "LEFT JOIN branches b ON b.ifsc_code = a.ifsc_code " +
		            "LEFT JOIN account_applications aa ON aa.user_id = u.user_id AND aa.status='APPROVED' " +
		            "LEFT JOIN users r ON r.user_id = aa.reviewed_by " +
		            "ORDER BY aa.reviewed_at DESC";

		    try (Connection conn = DBConnection.getConnection();
		         PreparedStatement ps = conn.prepareStatement(sql);
		         ResultSet rs = ps.executeQuery()) {

		        while (rs.next()) {
		            Map<String, Object> row = new HashMap<>();

		            row.put("userId", rs.getInt("user_id"));
		            row.put("username", rs.getString("username"));
		            row.put("email", rs.getString("email"));
		            row.put("phone", rs.getString("phone"));

		            row.put("firstName", rs.getString("first_name"));
		            row.put("lastName", rs.getString("last_name"));
		            row.put("dob", rs.getDate("dob"));
		            row.put("addressLine1", rs.getString("address_line1"));
		            row.put("addressLine2", rs.getString("address_line2"));
		            row.put("city", rs.getString("city"));
		            row.put("state", rs.getString("state"));
		            row.put("postalCode", rs.getString("postal_code"));
		            row.put("govtIdType", rs.getString("govt_id_type"));
		            row.put("govtIdValue", rs.getString("govt_id_value"));
		            row.put("kycStatus", rs.getString("kyc_status"));

		            row.put("accountId", rs.getInt("account_id"));
		            row.put("accountNumber", rs.getString("account_number"));
		            row.put("accountType", rs.getString("account_type"));
		            row.put("balance", rs.getDouble("balance"));
		            row.put("accountStatus", rs.getString("account_status"));

		            // New fields for bank details
		            row.put("ifscCode", rs.getString("ifsc_code"));
		            row.put("bankName", rs.getString("bank_name"));
		            row.put("branchName", rs.getString("branch_name"));
		            row.put("branchCity", rs.getString("branch_city"));

		            row.put("applicationId", rs.getInt("application_id"));
		            row.put("initialDeposit", rs.getDouble("initial_deposit"));
		            row.put("appCreated", rs.getTimestamp("app_created"));
		            row.put("reviewedBy", rs.getString("reviewed_by"));
		            row.put("reviewedByName", rs.getString("reviewed_by_name")); // new
		            row.put("reviewedAt", rs.getTimestamp("reviewed_at"));
		            row.put("remarks", rs.getString("remarks"));

		            approvedList.add(row);
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }

		    return approvedList;
		}

	 
	 
	 public List<Map<String, Object>> getApplicationsByStatusAndFilters(
		        String status, String dateFrom, String dateTo, String accountType) {
		    
		    List<Map<String, Object>> list = new ArrayList<>();
		    
		    StringBuilder sql = new StringBuilder(
		        "SELECT u.user_id, u.username, u.email, u.phone, " +
		        "c.first_name, c.last_name, c.dob, c.address_line1, c.address_line2, c.city, c.state, c.postal_code, " +
		        "c.govt_id_type, c.govt_id_value, c.kyc_status, " +
		        "a.account_id, a.account_number, a.account_type, a.balance, a.status AS account_status, a.ifsc_code, " +
		        "b.bank_name, b.branch_name, b.city AS branch_city, " +
		        "aa.application_id, aa.initial_deposit, aa.created_at AS app_created,  aa.status AS acc_status, " +
		        "aa.reviewed_by, r.username AS reviewed_by_name, " +
		        "aa.reviewed_at, aa.remarks " +
		        "FROM users u " +
		        "JOIN customers c ON c.customer_id = u.user_id " +
		        "LEFT JOIN accounts a ON a.customer_id = c.customer_id " +
		        "LEFT JOIN branches b ON b.ifsc_code = a.ifsc_code " +
		        "LEFT JOIN account_applications aa ON aa.user_id = u.user_id " +
		        "LEFT JOIN users r ON r.user_id = aa.reviewed_by " +
		        "WHERE 1=1 "
		    );

		    List<Object> params = new ArrayList<>();
		    if (status != null && !status.isBlank()) {
		        sql.append(" AND aa.status = ? ");
		        params.add(status);
		    }
		    if (dateFrom != null && !dateFrom.isBlank()) {
		        sql.append(" AND aa.created_at >= ? ");
		        params.add(Date.valueOf(dateFrom));
		    }
		    if (dateTo != null && !dateTo.isBlank()) {
		        sql.append(" AND aa.created_at <= ? ");
		        params.add(Date.valueOf(dateTo));
		    }
		    if (accountType != null && !accountType.isBlank()) {
		        sql.append(" AND a.account_type = ? ");
		        params.add(accountType);
		    }
//		    if (kycStatus != null && !kycStatus.isBlank()) {
//		        sql.append(" AND c.kyc_status = VERIFIED ");
//		        params.add(kycStatus);
//		    }
		    sql.append(" AND c.kyc_status = 'VERIFIED' ");

		    sql.append(" ORDER BY aa.reviewed_at DESC ");

		    try (Connection connection = DBConnection.getConnection();
		    		PreparedStatement ps = connection.prepareStatement(sql.toString())) {
		        for (int i = 0; i < params.size(); i++) {
		            ps.setObject(i + 1, params.get(i));
		        }
		        try (ResultSet rs = ps.executeQuery()) {
		            while (rs.next()) {
		                Map<String, Object> map = new HashMap<>();
		                
		                // Existing fields
		                map.put("user_id", rs.getInt("user_id"));
		                map.put("username", rs.getString("username"));
		                map.put("email", rs.getString("email"));
		                map.put("phone", rs.getString("phone"));
		                map.put("first_name", rs.getString("first_name"));
		                map.put("last_name", rs.getString("last_name"));
		                map.put("dob", rs.getDate("dob"));
		                map.put("address_line1", rs.getString("address_line1"));
		                map.put("address_line2", rs.getString("address_line2"));
		                map.put("city", rs.getString("city"));
		                map.put("state", rs.getString("state"));
		                map.put("postal_code", rs.getString("postal_code"));
		                map.put("govt_id_type", rs.getString("govt_id_type"));
		                map.put("govt_id_value", rs.getString("govt_id_value"));
		                map.put("kyc_status", rs.getString("kyc_status"));
		                map.put("account_id", rs.getInt("account_id"));
		                map.put("account_number", rs.getString("account_number"));
		                map.put("account_type", rs.getString("account_type"));
		                map.put("balance", rs.getDouble("balance"));
		                map.put("account_status", rs.getString("account_status"));
		                map.put("application_id", rs.getInt("application_id"));
		                map.put("initial_deposit", rs.getDouble("initial_deposit"));
		                map.put("app_created", rs.getTimestamp("app_created"));
		                map.put("reviewed_by", rs.getString("reviewed_by"));
		                map.put("reviewed_by_name", rs.getString("reviewed_by_name")); // new field
		                map.put("reviewed_at", rs.getTimestamp("reviewed_at"));
		                map.put("remarks", rs.getString("remarks"));
		                map.put("status", rs.getString("acc_status"));
		                
		                // New bank fields
		                map.put("ifsc_code", rs.getString("ifsc_code"));
		                map.put("bank_name", rs.getString("bank_name"));
		                map.put("branch_name", rs.getString("branch_name"));
		                map.put("branch_city", rs.getString("branch_city"));
		                
		                list.add(map);
		            }
		        } catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    return list;
		}


	 
	 
	 // The goal is to have one row per user, and if a customer has multiple accounts,
	 //we’ll aggregate them into a list of accounts inside the map. 
	 //The other fields (user info, customer info, application info) remain the same
	 
	 public List<Map<String, Object>> getApplicationsAggregatedByUser(
		        String status, String dateFrom, String dateTo, String accountType){

		    Map<Integer, Map<String, Object>> userMap = new LinkedHashMap<>(); // userId -> full map

		    StringBuilder sql = new StringBuilder(
		        "SELECT u.user_id, u.username, u.email, u.phone, " +
		        "c.first_name, c.last_name, c.dob, c.address_line1, c.address_line2, c.city, c.state, c.postal_code, " +
		        "c.govt_id_type, c.govt_id_value, c.kyc_status, " +
		        "a.account_id, a.account_number, a.account_type, a.balance, a.status AS account_status, a.ifsc_code, " +
		        "b.bank_name, b.branch_name, b.city AS branch_city, " +
		        "aa.application_id, aa.initial_deposit, aa.created_at AS app_created, aa.status AS acc_status, " +
		        "aa.reviewed_by, r.username AS reviewed_by_name, " +
		        "aa.reviewed_at, aa.remarks " +
		        "FROM users u " +
		        "JOIN customers c ON c.customer_id = u.user_id " +
		        "LEFT JOIN accounts a ON a.customer_id = c.customer_id " +
		        "LEFT JOIN branches b ON b.ifsc_code = a.ifsc_code " +
		        "LEFT JOIN account_applications aa ON aa.user_id = u.user_id " +
		        "LEFT JOIN users r ON r.user_id = aa.reviewed_by " +
		        "WHERE 1=1 "
		    );

		    List<Object> params = new ArrayList<>();
		    if (status != null && !status.isBlank()) {
		        sql.append(" AND aa.status = ? ");
		        params.add(status);
		    }
		    if (dateFrom != null && !dateFrom.isBlank()) {
		        sql.append(" AND aa.created_at >= ? ");
		        params.add(Date.valueOf(dateFrom));
		    }
		    if (dateTo != null && !dateTo.isBlank()) {
		        sql.append(" AND aa.created_at <= ? ");
		        params.add(Date.valueOf(dateTo));
		    }
		    if (accountType != null && !accountType.isBlank()) {
		        sql.append(" AND a.account_type = ? ");
		        params.add(accountType);
		    }
		    sql.append(" AND c.kyc_status = 'VERIFIED' ");
		    sql.append(" ORDER BY aa.reviewed_at DESC ");

		    try (Connection connection = DBConnection.getConnection();
		    		PreparedStatement ps = connection.prepareStatement(sql.toString())) {
		        for (int i = 0; i < params.size(); i++) {
		            ps.setObject(i + 1, params.get(i));
		        }

		        try (ResultSet rs = ps.executeQuery()) {
		            while (rs.next()) {
		                int userId = rs.getInt("user_id");

		                // Check if we already have this user
		                Map<String, Object> userEntry = userMap.get(userId);
		                if (userEntry == null) {
		                    userEntry = new HashMap<>();
		                    userEntry.put("user_id", userId);
		                    userEntry.put("username", rs.getString("username"));
		                    userEntry.put("email", rs.getString("email"));
		                    userEntry.put("phone", rs.getString("phone"));

		                    userEntry.put("first_name", rs.getString("first_name"));
		                    userEntry.put("last_name", rs.getString("last_name"));
		                    userEntry.put("dob", rs.getDate("dob"));
		                    userEntry.put("address_line1", rs.getString("address_line1"));
		                    userEntry.put("address_line2", rs.getString("address_line2"));
		                    userEntry.put("city", rs.getString("city"));
		                    userEntry.put("state", rs.getString("state"));
		                    userEntry.put("postal_code", rs.getString("postal_code"));
		                    userEntry.put("govt_id_type", rs.getString("govt_id_type"));
		                    userEntry.put("govt_id_value", rs.getString("govt_id_value"));
		                    userEntry.put("kyc_status", rs.getString("kyc_status"));

		                    // Application info
		                    userEntry.put("application_id", rs.getInt("application_id"));
		                    userEntry.put("initial_deposit", rs.getDouble("initial_deposit"));
		                    userEntry.put("app_created", rs.getTimestamp("app_created"));
		                    userEntry.put("reviewed_by", rs.getString("reviewed_by"));
		                    userEntry.put("reviewed_by_name", rs.getString("reviewed_by_name"));
		                    userEntry.put("reviewed_at", rs.getTimestamp("reviewed_at"));
		                    userEntry.put("remarks", rs.getString("remarks"));
		                    userEntry.put("status", rs.getString("acc_status"));

		                    // Initialize account list
		                    userEntry.put("accounts", new ArrayList<Map<String, Object>>());

		                    userMap.put(userId, userEntry);
		                }

		                // Add account info if account exists
		                int accountId = rs.getInt("account_id");
		                if (!rs.wasNull()) {
		                    Map<String, Object> account = new HashMap<>();
		                    account.put("account_id", accountId);
		                    account.put("account_number", rs.getString("account_number"));
		                    account.put("account_type", rs.getString("account_type"));
		                    account.put("balance", rs.getDouble("balance"));
		                    account.put("account_status", rs.getString("account_status"));
		                    account.put("ifsc_code", rs.getString("ifsc_code"));
		                    account.put("bank_name", rs.getString("bank_name"));
		                    account.put("branch_name", rs.getString("branch_name"));
		                    account.put("branch_city", rs.getString("branch_city"));

		                    @SuppressWarnings("unchecked")
		                    List<Map<String, Object>> accounts = (List<Map<String, Object>>) userEntry.get("accounts");
		                    accounts.add(account);
		                }
		            }
		        }
		        catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		    return new ArrayList<>(userMap.values());
		}



}
