package src.dao;

import src.entity.Staff;
import src.util.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    /**
     * 插入新教职工
     * @param staff Staff 对象
     * @return 是否成功
     */
    public boolean insertStaff(Staff staff) {
        // SQL 插入语句
        String sql = "INSERT INTO Staff (EID, EName, Dept, Phone, Position, Password) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // 设置参数
            pstmt.setString(1, staff.getEid());
            pstmt.setString(2, staff.getEName());
            pstmt.setString(3, staff.getDept());
            pstmt.setString(4, staff.getPhone());
            pstmt.setString(5, staff.getPosition());
            pstmt.setString(6, staff.getPassword());
            // 执行插入
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 删除教职工
     * @param eid 工号
     * @return 是否成功
     */
    public boolean deleteStaff(String eid) {
        // SQL 删除语句
        String sql = "DELETE FROM Staff WHERE EID = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // 设置参数
            pstmt.setString(1, eid);
            // 执行删除
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 更新教职工信息
     * @param staff Staff 对象
     * @return 是否成功
     */
    public boolean updateStaff(Staff staff) {
        // SQL 更新语句
        String sql = "UPDATE Staff SET EName = ?, Dept = ?, Phone = ?, Position = ?, Password = ? WHERE EID = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // 设置参数
            pstmt.setString(1, staff.getEName());
            pstmt.setString(2, staff.getDept());
            pstmt.setString(3, staff.getPhone());
            pstmt.setString(4, staff.getPosition());
            pstmt.setString(5, staff.getPassword());
            pstmt.setString(6, staff.getEid());
            // 执行更新
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 查询所有教职工
     * @return 教职工列表
     */
    public List<Staff> selectAll() {
        List<Staff> staffList = new ArrayList<>();
        // SQL 查询语句
        String sql = "SELECT * FROM Staff";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            // 遍历结果集
            while (rs.next()) {
                Staff staff = new Staff(
                    rs.getString("EID"),
                    rs.getString("EName"),
                    rs.getString("Dept"),
                    rs.getString("Phone"),
                    rs.getString("Position"),
                    rs.getString("Password")
                );
                staffList.add(staff);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return staffList;
    }
}
