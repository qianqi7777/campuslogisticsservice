package src.service;

import src.dao.ReservationDAO;
import src.dao.VenueDAO;
import src.entity.Reservation;
import src.entity.Venue;
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
        // 参数校验
        if (reservation == null
            || reservation.getVenueID() <= 0
            || reservation.getReserverID() == null
            || reservation.getResTime() == null
            || reservation.getDuration() <= 0) {
            throw new IllegalArgumentException("预约信息不完整");
        }

        // 场馆可用性检查
        Venue v = venueDAO.selectById(reservation.getVenueID());
        if (v == null) {
            throw new RuntimeException("指定场馆不存在");
        }
        if (!"是".equals(v.getIsAvailable())) {
            throw new RuntimeException("场馆当前不可用");
        }

        // 简化实现：不做时段冲突检测，直接插入为待审核
        reservation.setAuditStatus("待审核");
        reservationDAO.insertReservation(reservation);
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
        if (resId <= 0 || auditStatus == null || auditStatus.trim().isEmpty()) {
            throw new IllegalArgumentException("参数不合法");
        }
        if (!auditStatus.equals("通过") && !auditStatus.equals("拒绝") && !auditStatus.equals("待审核")) {
            throw new IllegalArgumentException("不支持的审核状态");
        }

        // 若通过，可考虑把场馆标记为不可用（取决于业务），这里不修改场馆表，仅更新预约状态
        reservationDAO.updateAuditStatus(resId, auditStatus);
    }
}
