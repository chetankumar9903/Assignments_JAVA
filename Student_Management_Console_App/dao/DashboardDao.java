package com.aurionpro.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.aurionpro.database.DBConnection;
import com.aurionpro.model.Dashboard;

public class DashboardDao {
    private Connection conn;

    public DashboardDao() {
    	conn = DBConnection.getInstance().getConnection();
    }

    public List<Dashboard> getDashboardData() {
        List<Dashboard> dashboardList = new ArrayList<>();
        String query = """
                SELECT s.student_id, s.student_name ,
                       c.course_id, c.course_name, c.total_fee,
                       sc.paid_fee,
                       GROUP_CONCAT(DISTINCT sub.subject_name ORDER BY sub.subject_name SEPARATOR ', ') AS subjects,
                       GROUP_CONCAT(DISTINCT t.teacher_name ORDER BY t.teacher_name SEPARATOR ', ') AS teachers
                FROM students s
                JOIN student_course sc ON s.student_id = sc.student_id
                JOIN courses c ON c.course_id = sc.course_id
                LEFT JOIN subjects sub ON sub.course_id = c.course_id
                LEFT JOIN teacher_subject st ON sub.subject_id = st.subject_id
                LEFT JOIN teachers t ON t.teacher_id = st.teacher_id
                GROUP BY s.student_id, c.course_id;
                """;

        try (PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            int srNo = 1;
            while (rs.next()) {
                Dashboard entry = new Dashboard();
                entry.setSrNo(srNo++);
                entry.setStudentId(rs.getInt("student_id"));
                entry.setStudentName(rs.getString("student_name"));
                entry.setCourseName(rs.getString("course_name"));
                entry.setPaidFee(rs.getDouble("paid_fee"));
                double total = rs.getDouble("total_fee");
                entry.setTotalFee(total);
                entry.setPendingFee(total - entry.getPaidFee());
                entry.setSubjects(rs.getString("subjects"));
                entry.setTeachers(rs.getString("teachers"));

                dashboardList.add(entry);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dashboardList;
    }
}

