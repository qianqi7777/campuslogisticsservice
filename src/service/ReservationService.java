package src.service;

import src.dao.ReservationDAO;
import src.dao.VenueDAO;
import src.entity.Reservation;
import java.util.List;

/**
 * ReservationService：场馆预约业务层
 * 负责预约的校验、提交和审核等业务流程封装
 */
public class ReservationService {
    private ReservationDAO reservationDAO = new ReservationDAO();
    private VenueDAO venueDAO = new VenueDAO();

    /**
     * 新增预约（可在此处加入冲突检查、容量检查等业务规则）
     * @param reservation 预约信息
     */
    public void addReservation(Reservation reservation) {
        // TODO
        // 实现说明：
        // 1. 参数校验：检查 reservation 字段非空（venueID, reserverID, resTime, duration）
        // 2. 场地可用性检查：调用 venueDAO.selectById(reservation.getVenueID())，并检查 isAvailable 字段是否为 "是"
        // 3. 冲突检查：查询在相同日期/时段是否已有通过的预约（可由 ReservationDAO 实现判断重叠逻辑）
        // 4. 如果通过校验，则设置 AuditStatus 为 "待审核"（或默认值），并调用 reservationDAO.insertReservation(reservation)
        // 5. 事务与异常：如果需要多表更新（例如同时更新场馆状态），请在事务中执行并在异常时回滚
        // 6. 返回值/异常策略：方法可选择抛出自定义异常或返回 boolean 表示成功/失败并在消息中说明失败原因
    }

    public List<Reservation> selectByReserverId(String reserverId) {
        return reservationDAO.selectByReserverId(reserverId);
    }

    /**
     * 更新预约审核状态（管理员使用）
     * @param resId 预约 ID
     * @param auditStatus 审核状态
     */
    public void updateAuditStatus(int resId, String auditStatus) {
        // TODO
        // 实现说明：
        // 1. 参数校验：resId > 0，auditStatus 非空且为允许的值（例如 "通过","拒绝"）
        // 2. 如 auditStatus 为 "通过"，可能需要把对应场馆在预约时间段标记为不可用（视系统设计），可调用 venueDAO.updateIsAvailable
        // 3. 调用 reservationDAO.updateAuditStatus(resId, auditStatus) 执行数据库更新
        // 4. 记录审批日志（可选）
        // 5. 处理异常并明确返回/抛出错误信息
    }
}
