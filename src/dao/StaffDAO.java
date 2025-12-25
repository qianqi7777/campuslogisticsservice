package src.dao;

import src.entity.Staff;
import src.util.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * StaffDAO：教职工（管理员）相关的数据访问对象
 * 提供按工号和密码查询、按工号查询等方法
 */
public class StaffDAO {
    /**
     * 根据工号和密码查询教职工记录（用于登录验证）
     * @param eid 工号
     * @param password 密码
     * @return Staff 对象或 null
     */
    public Staff selectByEidAndPwd(String eid, String password) {
        // SQL 查询语句
        String sql = "SELECT * FROM Staff WHERE EID = ? AND Password = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // 设置参数
            pstmt.setString(1, eid);
            pstmt.setString(2, password);
            // 执行查询
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Staff(
                        rs.getString("EID"),
                        rs.getString("EName"),
                        rs.getString("Dept"),
                        rs.getString("Phone"),
                        rs.getString("Position"),
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
     * 根据工号和部门查询教职工（可用于按部门限制登陆或查找）
     * @param eid 工号
     * @param dept 部门
     * @return Staff 或 null
     */
    public Staff selectByEidAndDept(String eid, String dept) {
        // SQL 查询语句
        String sql = "SELECT * FROM Staff WHERE EID = ? AND Dept = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // 设置参数
            pstmt.setString(1, eid);
            pstmt.setString(2, dept);
            // 执行查询
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Staff(
                        rs.getString("EID"),
                        rs.getString("EName"),
                        rs.getString("Dept"),
                        rs.getString("Phone"),
                        rs.getString("Position"),
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
     * 根据工号查询教职工信息
     * @param eid 工号
     * @return Staff 或 null
     */
    public Staff selectByEid(String eid) {
        // SQL 查询语句
        String sql = "SELECT * FROM Staff WHERE EID = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // 设置参数
            pstmt.setString(1, eid);
            // 执行查询
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Staff(
                        rs.getString("EID"),
                        rs.getString("EName"),
                        rs.getString("Dept"),
                        rs.getString("Phone"),
                        rs.getString("Position"),
                        rs.getString("Password")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
