package src.dao;

import src.entity.Venue;
import src.util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * VenueDAO：场馆（Venue）相关的数据库访问对象
 * 提供场馆查询和状态更新操作
 */
public class VenueDAO {
    /**
     * 根据场馆ID查询场馆信息
     * @param venueId 场馆 ID
     * @return Venue 对象或 null
     */
    public Venue selectById(int venueId) {
        // SQL 查询语句
        String sql = "SELECT VenueID, VenueName, Capacity, Location, IsAvailable FROM Venue WHERE VenueID = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, venueId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                Venue v = new Venue();
                v.setVenueID(rs.getInt("VenueID"));
                v.setVenueName(rs.getString("VenueName"));
                v.setCapacity(rs.getInt("Capacity"));
                v.setLocation(rs.getString("Location"));
                v.setIsAvailable(rs.getString("IsAvailable"));
                return v;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }
        return null;
    }

    /**
     * 查询所有可用（isAvailable = '是'）的场馆
     * @return 可用场馆列表
     */
    public List<Venue> selectAvailableVenues() {
        List<Venue> list = new ArrayList<>();
        // SQL 查询语句，按场馆名称排序
        String sql = "SELECT VenueID, VenueName, Capacity, Location, IsAvailable FROM Venue WHERE IsAvailable = '是' ORDER BY VenueName";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Venue v = new Venue();
                v.setVenueID(rs.getInt("VenueID"));
                v.setVenueName(rs.getString("VenueName"));
                v.setCapacity(rs.getInt("Capacity"));
                v.setLocation(rs.getString("Location"));
                v.setIsAvailable(rs.getString("IsAvailable"));
                list.add(v);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }
        return list;
    }

    /**
     * 更新场馆是否可用状态（例如管理员审核后设置）
     * @param venueId 场馆 ID
     * @param isAvailable "是" 或 "否"
     */
    public void updateIsAvailable(int venueId, String isAvailable) {
        // SQL 更新语句
        String sql = "UPDATE Venue SET IsAvailable = ? WHERE VenueID = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, isAvailable);
            pstmt.setInt(2, venueId);
            int updated = pstmt.executeUpdate();
            if (updated <= 0) {
                System.out.println("updateIsAvailable: 没有找到匹配的场馆记录 (venueId=" + venueId + ")");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("更新场馆可用状态失败", e);
        } finally {
            DBUtil.close(conn, pstmt);
        }
    }

    /**
     * 插入新场馆
     * @param venue Venue 对象
     * @return 是否成功
     */
    public boolean insertVenue(Venue venue) {
        // SQL 插入语句
        String sql = "INSERT INTO Venue (VenueName, Capacity, Location, IsAvailable) VALUES (?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, venue.getVenueName());
            pstmt.setInt(2, venue.getCapacity());
            pstmt.setString(3, venue.getLocation());
            pstmt.setString(4, venue.getIsAvailable());
            int rows = pstmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("插入新场馆失败", e);
        } finally {
            DBUtil.close(conn, pstmt);
        }
    }

    /**
     * 更新场馆信息
     * @param venue Venue 对象
     * @return 是否成功
     */
    public boolean updateVenue(Venue venue) {
        // SQL 更新语句
        String sql = "UPDATE Venue SET VenueName = ?, Capacity = ?, Location = ?, IsAvailable = ? WHERE VenueID = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, venue.getVenueName());
            pstmt.setInt(2, venue.getCapacity());
            pstmt.setString(3, venue.getLocation());
            pstmt.setString(4, venue.getIsAvailable());
            pstmt.setInt(5, venue.getVenueID());
            int rows = pstmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("更新场馆信息失败", e);
        } finally {
            DBUtil.close(conn, pstmt);
        }
    }

    /**
     * 删除场馆
     * @param venueId 场馆ID
     * @return 是否成功
     */
    public boolean deleteVenue(int venueId) {
        // SQL 删除语句
        String sql = "DELETE FROM Venue WHERE VenueID = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, venueId);
            int rows = pstmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("删除场馆失败", e);
        } finally {
            DBUtil.close(conn, pstmt);
        }
    }

    /**
     * 查询所有场馆
     * @return 场馆列表
     */
    public List<Venue> selectAll() {
        List<Venue> list = new ArrayList<>();
        // SQL 查询语句，按场馆名称排序
        String sql = "SELECT VenueID, VenueName, Capacity, Location, IsAvailable FROM Venue ORDER BY VenueName";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Venue v = new Venue();
                v.setVenueID(rs.getInt("VenueID"));
                v.setVenueName(rs.getString("VenueName"));
                v.setCapacity(rs.getInt("Capacity"));
                v.setLocation(rs.getString("Location"));
                v.setIsAvailable(rs.getString("IsAvailable"));
                list.add(v);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }
        return list;
    }
}
