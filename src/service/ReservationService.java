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
    }
}
