package src.dao;

import src.entity.Student;
import src.util.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * StudentDAO：学生相关的数据库访问对象
 * 提供学生登录验证及相关查询方法
 */
public class StudentDAO {
    /**
     * 根据学号和密码查询学生（登录用）
     * @param sid 学号
     * @param password 密码
     * @return Student 对象或 null
     */
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

    /**
     * 根据学号查询所属学院（用于统计或权限判断）
     * @param sid 学号
     * @return 学院名称或 null
     */
    public String selectCollegeBySid(String sid) {
        // TODO: 实现查询学院的逻辑
        return null;
    }
}
