package src.service;

import src.dao.ReservationDAO;
import src.entity.Reservation;
import java.util.List;

/**
 * 预约服务类
 * 处理场地预约、审核、查询
 */
public class ReservationService {
    private ReservationDAO reservationDAO = new ReservationDAO();

    // --- 学生/职工端功能 ---

    /**
     * 查询场地状态
     * @param venueId 场地ID
     */
    public void checkVenueStatus(int venueId) {
        // TODO: 查询场地是否被预约
    }

    /**
     * 提交预约申请
     * @param reservation 预约信息
     */
    public void submitReservation(Reservation reservation) {
        // TODO: 插入预约记录，状态为待审核
    }

    /**
     * 获取用户的预约记录 (学生/职工)
     * @param userId 用户ID
     * @return 预约列表
     */
    public List<Reservation> getReservationsByUser(String userId) {
        // TODO: 调用 DAO 查询
        return null;
    }

    // --- 管理员功能 ---

    /**
     * 审核预约申请
     * @param resId 预约ID
     * @param pass 是否通过
     */
    public void auditReservation(int resId, boolean pass) {
        // TODO: 更新预约状态
    }

    /**
     * 获取所有预约记录 (管理员/职工)
     * @return 预约列表
     */
    public List<Reservation> getAllReservations() {
        // TODO: 调用 DAO 查询所有
        return null;
    }
}
