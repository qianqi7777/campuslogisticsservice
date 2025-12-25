package src.dao;

import src.entity.Student;
import src.util.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
        // SQL 查询语句
        String sql = "SELECT * FROM Student WHERE SID = ? AND Password = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // 设置参数
            pstmt.setString(1, sid);
            pstmt.setString(2, password);
            // 执行查询
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
        // SQL 查询语句
        String sql = "SELECT College FROM Student WHERE SID = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // 设置参数
            pstmt.setString(1, sid);
            // 执行查询
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("College");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 插入新学生
     * @param student Student 对象
     * @return 是否成功
     */
    public boolean insertStudent(Student student) {
        // SQL 插入语句
        String sql = "INSERT INTO Student (SID, SName, College, Phone, Grade, Password) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // 设置参数
            pstmt.setString(1, student.getSid());
            pstmt.setString(2, student.getSName());
            pstmt.setString(3, student.getCollege());
            pstmt.setString(4, student.getPhone());
            pstmt.setString(5, student.getGrade());
            pstmt.setString(6, student.getPassword());
            // 执行插入
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 删除学生
     * @param sid 学号
     * @return 是否成功
     */
    public boolean deleteStudent(String sid) {
        // SQL 删除语句
        String sql = "DELETE FROM Student WHERE SID = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // 设置参数
            pstmt.setString(1, sid);
            // 执行删除
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 更新学生信息
     * @param student Student 对象
     * @return 是否成功
     */
    public boolean updateStudent(Student student) {
        // SQL 更新语句
        String sql = "UPDATE Student SET SName = ?, College = ?, Phone = ?, Grade = ?, Password = ? WHERE SID = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // 设置参数
            pstmt.setString(1, student.getSName());
            pstmt.setString(2, student.getCollege());
            pstmt.setString(3, student.getPhone());
            pstmt.setString(4, student.getGrade());
            pstmt.setString(5, student.getPassword());
            pstmt.setString(6, student.getSid());
            // 执行更新
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 查询所有学生
     * @return 学生列表
     */
    public List<Student> selectAll() {
        List<Student> students = new ArrayList<>();
        // SQL 查询语句
        String sql = "SELECT * FROM Student";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            // 遍历结果集
            while (rs.next()) {
                students.add(new Student(
                    rs.getString("SID"),
                    rs.getString("SName"),
                    rs.getString("College"),
                    rs.getString("Phone"),
                    rs.getString("Grade"),
                    rs.getString("Password")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }
}
