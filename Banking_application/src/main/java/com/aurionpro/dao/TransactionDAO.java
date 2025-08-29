package com.aurionpro.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.aurionpro.database.DBConnection;
import com.aurionpro.model.Transaction;
import com.aurionpro.model.TransactionFilter;

public class TransactionDAO {
	public List<Transaction> fetchTransactions(TransactionFilter filter) {
        List<Transaction> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
            "SELECT t.txn_id, t.created_at, t.type, t.status, t.amount, t.description, " +
            "fa.account_number AS from_account_number, CONCAT(fc.first_name,' ',fc.last_name) AS from_customer_name, " +
            "ta.account_number AS to_account_number, CONCAT(tc.first_name,' ',tc.last_name) AS to_customer_name, " +
            "t.to_external_account_number, t.to_external_ifsc, u.username AS initiated_by " +
            "FROM transactions t " +
            "LEFT JOIN accounts fa ON t.from_account_id = fa.account_id " +
            "LEFT JOIN customers fc ON fa.customer_id = fc.customer_id " +
            "LEFT JOIN accounts ta ON t.to_account_id = ta.account_id " +
            "LEFT JOIN customers tc ON ta.customer_id = tc.customer_id " +
            "LEFT JOIN users u ON t.initiated_by = u.user_id " +
            "WHERE 1=1 "
        );

        List<Object> params = new ArrayList<>();

        if (filter.getFromDate() != null) {
            sql.append(" AND t.created_at >= ? ");
            params.add(new Timestamp(filter.getFromDate().getTime()));
        }
        if (filter.getToDate() != null) {
            sql.append(" AND t.created_at <= ? ");
            params.add(new Timestamp(filter.getToDate().getTime()));
        }
        if (filter.getTypes() != null && !filter.getTypes().isEmpty()) {
            sql.append(" AND t.type IN (");
            sql.append(String.join(",", filter.getTypes().stream().map(s -> "?").toArray(String[]::new)));
            sql.append(") ");
            params.addAll(filter.getTypes());
        }
        if (filter.getStatuses() != null && !filter.getStatuses().isEmpty()) {
            sql.append(" AND t.status IN (");
            sql.append(String.join(",", filter.getStatuses().stream().map(s -> "?").toArray(String[]::new)));
            sql.append(") ");
            params.addAll(filter.getStatuses());
        }
        if (filter.getAccountNumber() != null && !filter.getAccountNumber().isEmpty()) {
            sql.append(" AND (fa.account_number = ? OR ta.account_number = ?) ");
            params.add(filter.getAccountNumber());
            params.add(filter.getAccountNumber());
        }
        if (filter.getCustomerName() != null && !filter.getCustomerName().isEmpty()) {
            sql.append(" AND (fc.first_name LIKE ? OR fc.last_name LIKE ? OR tc.first_name LIKE ? OR tc.last_name LIKE ?) ");
            String namePattern = "%" + filter.getCustomerName() + "%";
            params.add(namePattern); params.add(namePattern);
            params.add(namePattern); params.add(namePattern);
        }

        sql.append(" ORDER BY t.created_at  ");

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Transaction txn = new Transaction();
                txn.setTxnId(rs.getInt("txn_id"));
                txn.setTxnDate(rs.getTimestamp("created_at"));
                txn.setType(rs.getString("type"));
                txn.setStatus(rs.getString("status"));
                txn.setAmount(rs.getBigDecimal("amount"));
                txn.setDescription(rs.getString("description"));
                txn.setFromAccountNumber(rs.getString("from_account_number"));
                txn.setFromCustomerName(rs.getString("from_customer_name"));
                txn.setToAccountNumber(rs.getString("to_account_number"));
                txn.setToCustomerName(rs.getString("to_customer_name"));
                txn.setToExternalAccountNumber(rs.getString("to_external_account_number"));
                txn.setToExternalIfsc(rs.getString("to_external_ifsc"));
                txn.setInitiatedBy(rs.getString("initiated_by"));

                list.add(txn);
            }
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        return list;
    }

}
