package src.dao;

import src.entity.Reservation;
import src.util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ReservationDAO：场馆预约相关的 DAO
 * 负责预约记录的增查改操作
 */
public class ReservationDAO {
    /**
     * 插入一条预约记录
     * @param reservation 要插入的 Reservation 对象
     */
    public void insertReservation(Reservation reservation) {
        String sql = "INSERT INTO Reservation (VenueID, ReserverID, ResTime, Duration, AuditStatus) VALUES (?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, reservation.getVenueID());
            pstmt.setString(2, reservation.getReserverID());
            pstmt.setDate(3, reservation.getResTime());
            pstmt.setInt(4, reservation.getDuration());
            pstmt.setString(5, reservation.getAuditStatus());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            if (rs != null && rs.next()) {
                reservation.setResID(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("插入预约记录失败", e);
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }
    }

    /**
     * 根据预约 ID 查询预约信息
     * @param resId 预约 ID
     * @return Reservation 对象或 null
     */
    public Reservation selectById(int resId) {
        String sql = "SELECT ResID, VenueID, ReserverID, ResTime, Duration, AuditStatus FROM Reservation WHERE ResID = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, resId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                Reservation r = new Reservation();
                r.setResID(rs.getInt("ResID"));
                r.setVenueID(rs.getInt("VenueID"));
                r.setReserverID(rs.getString("ReserverID"));
                r.setResTime(rs.getDate("ResTime"));
                r.setDuration(rs.getInt("Duration"));
                r.setAuditStatus(rs.getString("AuditStatus"));
                return r;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }
        return null;
    }

    /**
     * 根据预约者 ID 查询其所有预约记录
     * @param reserverId 预约者学号
     * @return 预约记录列表
     */
    public List<Reservation> selectByReserverId(String reserverId) {
        List<Reservation> list = new ArrayList<>();
        String sql = "SELECT ResID, VenueID, ReserverID, ResTime, Duration, AuditStatus FROM Reservation WHERE ReserverID = ? ORDER BY ResTime DESC";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, reserverId);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Reservation r = new Reservation();
                r.setResID(rs.getInt("ResID"));
                r.setVenueID(rs.getInt("VenueID"));
                r.setReserverID(rs.getString("ReserverID"));
                r.setResTime(rs.getDate("ResTime"));
                r.setDuration(rs.getInt("Duration"));
                r.setAuditStatus(rs.getString("AuditStatus"));
                list.add(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }
        return list;
    }

    /**
     * 更新预约的审核状态（管理员审核后调用）
     * @param resId 预约 ID
     * @param auditStatus 审核状态，如 "通过"、"拒绝"
     */
    public void updateAuditStatus(int resId, String auditStatus) {
        String sql = "UPDATE Reservation SET AuditStatus = ? WHERE ResID = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, auditStatus);
            pstmt.setInt(2, resId);
            int updated = pstmt.executeUpdate();
            if (updated <= 0) {
                System.out.println("updateAuditStatus: 没有找到匹配的预约记录 (resId=" + resId + ")");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("更新预约审核状态失败", e);
        } finally {
            DBUtil.close(conn, pstmt);
        }
    }
}
