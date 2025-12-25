package src.dao;

import src.entity.CampusCard;
import src.util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * CampusCardDAO：校园卡相关的数据库访问对象（DAO）
 * 负责对 CampusCard 表进行增删改查操作（数据访问层）
 */
public class CampusCardDAO {
    /**
     * 根据用户ID和用户类型查询校园卡信息
     * @param userId 关联的用户ID（例如学号或工号）
     * @param userType 用户类型（例如 "student" 或 "staff"）
     * @return CampusCard 若存在则返回对应 CampusCard 对象，否则返回 null
     */
    public CampusCard selectByUserIdAndType(String userId, String userType) {
        // 定义 SQL 查询语句，根据 UserID 和 UserType 查询校园卡信息
        String sql = "SELECT CardID, UserID, UserType, Balance, Status, CreateTime FROM CampusCard WHERE UserID = ? AND UserType = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            // 获取数据库连接
            conn = DBUtil.getConnection();
            // 创建 PreparedStatement 对象
            pstmt = conn.prepareStatement(sql);
            // 设置 SQL 参数
            pstmt.setString(1, userId);
            pstmt.setString(2, userType);
            // 执行查询
            rs = pstmt.executeQuery();
            // 处理结果集
            if (rs.next()) {
                CampusCard card = new CampusCard();
                card.setCardID(rs.getString("CardID"));
                card.setUserID(rs.getString("UserID"));
                card.setUserType(rs.getString("UserType"));
                card.setBalance(rs.getBigDecimal("Balance"));
                card.setStatus(rs.getString("Status"));
                card.setCreateTime(rs.getTimestamp("CreateTime"));
                return card;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 关闭数据库资源
            DBUtil.close(conn, pstmt, rs);
        }
        return null;
    }

    /**
     * 根据用户ID和类型更新校园卡状态（例如挂失/恢复）
     * @param userId 关联用户ID
     * @param userType 用户类型
     * @param status 要设置的状态字符串
     */
    public void updateStatus(String userId, String userType, String status) {
        // 定义 SQL 更新语句
        String sql = "UPDATE CampusCard SET Status = ? WHERE UserID = ? AND UserType = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            // 获取连接
            conn = DBUtil.getConnection();
            // 预编译 SQL
            pstmt = conn.prepareStatement(sql);
            // 设置参数
            pstmt.setString(1, status);
            pstmt.setString(2, userId);
            pstmt.setString(3, userType);
            // 执行更新
            int updated = pstmt.executeUpdate();
            // 可选：打印更新结果
            if (updated <= 0) {
                System.out.println("updateStatus: 没有找到匹配的校园卡记录 (userId=" + userId + ", userType=" + userType + ")");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("更新校园卡状态失败", e);
        } finally {
            // 释放资源
            DBUtil.close(conn, pstmt);
        }
    }

    /**
     * 根据卡号查询校园卡信息
     * @param cardId 校园卡卡号
     * @return CampusCard 对象或 null
     */
    public CampusCard selectByCardId(String cardId) {
        // SQL 查询语句
        String sql = "SELECT CardID, UserID, UserType, Balance, Status, CreateTime FROM CampusCard WHERE CardID = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, cardId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                CampusCard card = new CampusCard();
                card.setCardID(rs.getString("CardID"));
                card.setUserID(rs.getString("UserID"));
                card.setUserType(rs.getString("UserType"));
                card.setBalance(rs.getBigDecimal("Balance"));
                card.setStatus(rs.getString("Status"));
                card.setCreateTime(rs.getTimestamp("CreateTime"));
                return card;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }
        return null;
    }

    /**
     * 根据卡号更新状态（用于恢复或挂失）
     * @param cardId 卡号
     * @param status 新状态
     */
    public void updateStatusByCardId(String cardId, String status) {
        // SQL 更新语句
        String sql = "UPDATE CampusCard SET Status = ? WHERE CardID = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, status);
            pstmt.setString(2, cardId);
            int updated = pstmt.executeUpdate();
            if (updated <= 0) {
                System.out.println("updateStatusByCardId: 没有找到匹配的校园卡记录 (cardId=" + cardId + ")");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("按卡号更新校园卡状态失败", e);
        } finally {
            DBUtil.close(conn, pstmt);
        }
    }

    /**
     * 查询所有处于“挂失”或“丢失”状态的卡（用于管理端查看）
     * @return CampusCard 列表
     */
    public List<CampusCard> selectLostCards() {
        List<CampusCard> list = new ArrayList<>();
        // 查询状态为 挂失 或 丢失 的记录
        String sql = "SELECT CardID, UserID, UserType, Balance, Status, CreateTime FROM CampusCard WHERE Status IN ('挂失','丢失')";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                CampusCard card = new CampusCard();
                card.setCardID(rs.getString("CardID"));
                card.setUserID(rs.getString("UserID"));
                card.setUserType(rs.getString("UserType"));
                card.setBalance(rs.getBigDecimal("Balance"));
                card.setStatus(rs.getString("Status"));
                card.setCreateTime(rs.getTimestamp("CreateTime"));
                list.add(card);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }
        return list;
    }
}
