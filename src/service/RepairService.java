package src.service;

import src.dao.RepairDAO;
import src.dao.StaffDAO;
import src.entity.Repair;

/**
 * RepairService：维修业务层
 * 封装维修相关的业务逻辑，包括提交维修、分配处理人、以及完成更新等
 */
public class RepairService {
    private RepairDAO repairDAO = new RepairDAO();
    private StaffDAO staffDAO = new StaffDAO();

    /**
     * 提交新的维修申请（业务层封装）
     * @param repair 要提交的维修实体
     */
    public void addRepair(Repair repair) {
        // TODO
    }

    /**
     * 分配维修任务给指定教职工
     * @param repairId 维修单 ID
     * @param handlerId 处理人工号
     */
    public void assignRepairTask(int repairId, String handlerId) {
        // TODO
    }

    /**
     * 将维修单标记为已完成
     * @param repairId 维修单 ID
     */
    public void updateToCompleted(int repairId) {
        // TODO
    }
}
