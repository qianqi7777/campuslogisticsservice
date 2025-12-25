package src.service;

import src.dao.ReservationDAO;
import src.dao.VenueDAO;
import src.entity.Reservation;
import src.entity.Venue;
import java.util.List;

/**
 * 预约服务类
 * 处理场地预约、审核、查询
 */
public class ReservationService {
    private ReservationDAO reservationDAO = new ReservationDAO();
    private VenueDAO venueDAO = new VenueDAO();

    // --- 学生/职工端功能 ---

    /**
     * 查询场地状态
     * @param venueId 场地ID
     */
    public void checkVenueStatus(int venueId) {
        Venue v = venueDAO.selectById(venueId);
        if (v == null) {
            System.out.println("场地不存在");
            return;
        }
        System.out.println("场地: " + v.getVenueName() + ", 状态: " + v.getIsAvailable());
        List<Reservation> list = reservationDAO.selectByVenue(venueId);
        System.out.println("已有预约:");
        for (Reservation r : list) {
            System.out.println("时间: " + r.getResTime() + ", 时长: " + r.getDuration() + ", 状态: " + r.getAuditStatus());
        }
    }

    /**
     * 提交预约申请
     * @param reservation 预约信息
     */
    public void submitReservation(Reservation reservation) {
        reservation.setAuditStatus("待审核");
        reservationDAO.insertReservation(reservation);
    }

    /**
     * 获取用户的预约记录 (学生/职工)
     * @param userId 用户ID
     * @return 预约列表
     */
    public List<Reservation> getReservationsByUser(String userId) {
        return reservationDAO.selectByReserver(userId);
    }

    // --- 管理员功能 ---

    /**
     * 审核预约申请
     * @param resId 预约ID
     * @param pass 是否通过
     */
    public void auditReservation(int resId, boolean pass) {
        String status = pass ? "通过" : "拒绝";
        reservationDAO.updateAuditStatus(resId, status);
    }

    /**
     * 获取所有预约记录 (管理员/职工)
     * @return 预约列表
     */
    public List<Reservation> getAllReservations() {
        return reservationDAO.selectAll();
    }
}
