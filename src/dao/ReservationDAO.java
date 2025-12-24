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
        // TODO
        // 实现说明：
        // 1. SQL: INSERT INTO Reservation (VenueID, ReserverID, ResTime, Duration, AuditStatus) VALUES (?, ?, ?, ?, ?)
        // 2. 获取连接并创建 PreparedStatement，设置参数
        // 3. 执行更新并可获取生成的主键（ResID）
        // 4. 关闭资源并处理异常
    }

    /**
     * 根据预约 ID 查询预约信息
     * @param resId 预约 ID
     * @return Reservation 对象或 null
     */
    public Reservation selectById(int resId) {
        // TODO
        // 实现说明：
        // 1. SQL: SELECT ResID, VenueID, ReserverID, ResTime, Duration, AuditStatus FROM Reservation WHERE ResID = ?
        // 2. 执行查询并映射结果到 Reservation
        // 3. 关闭资源并返回
        return null;
    }

    /**
     * 根据预约者 ID 查询其所有预约记录
     * @param reserverId 预约者学号
     * @return 预约记录列表
     */
    public List<Reservation> selectByReserverId(String reserverId) {
        // TODO
        // 实现说明：
        // 1. SQL: SELECT * FROM Reservation WHERE ReserverID = ? ORDER BY ResTime DESC
        // 2. 执行查询并将结果映射到 List<Reservation>
        // 3. 关闭资源并返回列表
        return new ArrayList<>();
    }

    /**
     * 更新预约的审核状态（管理员审核后调用）
     * @param resId 预约 ID
     * @param auditStatus 审核状态，如 "通过"、"拒绝"
     */
    public void updateAuditStatus(int resId, String auditStatus) {
        // TODO
        // 实现说明：
        // 1. SQL: UPDATE Reservation SET AuditStatus = ? WHERE ResID = ?
        // 2. 执行更新并处理受影响行数
        // 3. 关闭资源并处理异常
    }
}
