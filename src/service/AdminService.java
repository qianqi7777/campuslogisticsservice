package src.service;

import src.dao.StaffDAO;
import src.entity.Staff;
import src.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * AdminService：管理员业务层
 * 封装与管理员相关的业务逻辑（例如登录、统计等），由 View 层调用
 */
public class AdminService {
    private StaffDAO staffDAO = new StaffDAO();

    /**
     * 管理员登录
     * @param eid 工号
     * @param password 密码
     * @return Staff 登录成功返回 Staff 对象，否则返回 null
     */
    public Staff login(String eid, String password) {
        return staffDAO.selectByEidAndPwd(eid, password);
    }

    /**
     * 按学院统计维修数量（通过 Repair 与 Student 表联表统计）
     */
    public void statRepairByCollege() {
        String sql = "SELECT s.College, COUNT(r.RepairID) AS cnt " +
                     "FROM Repair r JOIN Student s ON r.SubmitterID = s.SID " +
                     "GROUP BY s.College";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            System.out.println("学院\t维修数量");
            while (rs.next()) {
                String college = rs.getString("College");
                int cnt = rs.getInt("cnt");
                System.out.println(college + "\t" + cnt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("统计维修数量失败", e);
        }
    }

    /**
     * 统计维修评价（示例：按评分分布统计）
     */
    public void statRepairEvaluation() {
        String sql = "SELECT Score, COUNT(*) AS cnt FROM Evaluation GROUP BY Score ORDER BY Score DESC";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            System.out.println("评分\t数量");
            while (rs.next()) {
                int score = rs.getInt("Score");
                int cnt = rs.getInt("cnt");
                System.out.println(score + "\t" + cnt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("统计维修评价失败", e);
        }
    }
}
