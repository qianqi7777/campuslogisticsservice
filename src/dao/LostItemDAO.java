package src.dao;

import src.entity.LostItem;
import src.util.DBUtil;
import java.sql.*;

/**
 * LostItemDAO：失物招领相关的数据库访问对象
 * 提供失物信息的插入等操作
 */
public class LostItemDAO {
    /**
     * 插入一条失物招领记录
     * @param lostItem 要插入的 LostItem 对象
     */
    public void insertLostItem(LostItem lostItem) {
        // 实现说明：
        // 1. SQL: INSERT INTO LostItem (ItemName, Place, PublishTime, Status, PublisherID) VALUES (?, ?, ?, ?, ?)
        //    - PublishTime 可使用 CURRENT_TIMESTAMP 或由 Java 端传入
        // 2. 获取连接并创建 PreparedStatement，设置参数
        // 3. 执行更新，并在需要时通过 getGeneratedKeys 获取 ItemID
        // 4. 关闭资源并处理异常
        String sql = "INSERT INTO LostItem (ItemName, Place, PublishTime, Status, PublisherID) VALUES (?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            // 获取数据库连接
            conn = DBUtil.getConnection();
            // 创建 PreparedStatement，并指定返回生成的主键
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            // 设置参数
            pstmt.setString(1, lostItem.getItemName());
            pstmt.setString(2, lostItem.getPlace());
            // 如果发布时间为空，则使用当前系统时间
            if (lostItem.getPublishTime() != null) {
                pstmt.setTimestamp(3, lostItem.getPublishTime());
            } else {
                pstmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            }
            pstmt.setString(4, lostItem.getStatus());
            pstmt.setString(5, lostItem.getPublisherID());
            // 执行插入
            pstmt.executeUpdate();
            // 获取自增主键
            rs = pstmt.getGeneratedKeys();
            if (rs != null && rs.next()) {
                lostItem.setItemID(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("插入失物记录失败", e);
        } finally {
            // 关闭资源
            DBUtil.close(conn, pstmt, rs);
        }
    }
}
