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
        String sql = "INSERT INTO LostItem (ItemName, Place, PublishTime, Status, PublisherID) VALUES (?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, lostItem.getItemName());
            pstmt.setString(2, lostItem.getPlace());
            if (lostItem.getPublishTime() != null) {
                pstmt.setTimestamp(3, lostItem.getPublishTime());
            } else {
                pstmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            }
            pstmt.setString(4, lostItem.getStatus());
            pstmt.setString(5, lostItem.getPublisherID());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            if (rs != null && rs.next()) {
                lostItem.setItemID(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("插入失物记录失败", e);
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }
    }
}
