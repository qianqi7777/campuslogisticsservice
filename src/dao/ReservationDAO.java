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
    }

    /**
     * 根据预约 ID 查询预约信息
     * @param resId 预约 ID
     * @return Reservation 对象或 null
     */
    public Reservation selectById(int resId) {
        // TODO
        return null;
    }

    /**
     * 根据预约者 ID 查询其所有预约记录
     * @param reserverId 预约者学号
     * @return 预约记录列表
     */
    public List<Reservation> selectByReserverId(String reserverId) {
        // TODO
        return new ArrayList<>();
    }

    /**
     * 更新预约的审核状态（管理员审核后调用）
     * @param resId 预约 ID
     * @param auditStatus 审核状态，如 "通过"、"拒绝"
     */
    public void updateAuditStatus(int resId, String auditStatus) {
        // TODO
    }
}
