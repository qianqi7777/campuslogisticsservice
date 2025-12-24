package src.dao;

import src.entity.Student;
import src.util.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentDAO {
    public Student selectBySidAndPwd(String sid, String password) {
        String sql = "SELECT * FROM Student WHERE SID = ? AND Password = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, sid);
            pstmt.setString(2, password);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Student(
                        rs.getString("SID"),
                        rs.getString("SName"),
                        rs.getString("College"),
                        rs.getString("Phone"),
                        rs.getString("Grade"),
                        rs.getString("Password")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String selectCollegeBySid(String sid) {
        // TODO: 实现查询学院逻辑
        return null;
    }
}
