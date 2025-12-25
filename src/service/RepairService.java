package src.service;

import src.dao.RepairDAO;
import src.dao.StaffDAO;
import src.entity.Repair;
import src.entity.Staff;

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
        // 基础参数校验
        if (repair == null || repair.getContent() == null || repair.getContent().trim().isEmpty()
            || repair.getSubmitterID() == null || repair.getSubmitterID().trim().isEmpty()) {
            throw new IllegalArgumentException("维修申请信息不完整");
        }
        // 默认状态为待处理
        if (repair.getStatus() == null) {
            repair.setStatus("待处理");
        }
        // 调用 DAO 插入维修记录
        repairDAO.insertRepair(repair);
    }

    /**
     * 分配维修任务给指定教职工
     * @param repairId 维修单 ID
     * @param handlerId 处理人工号
     */
    public void assignRepairTask(int repairId, String handlerId) {
        // 参数校验
        if (repairId <= 0 || handlerId == null || handlerId.trim().isEmpty()) {
            throw new IllegalArgumentException("参数不合法");
        }
        // 检查处理人工号是否存在
        Staff s = staffDAO.selectByEid(handlerId);
        if (s == null) {
            throw new RuntimeException("指定处理人工号不存在");
        }
        // 简单检查维修单是否存在
        if (repairDAO.selectById(repairId) == null) {
            throw new RuntimeException("维修单不存在");
        }
        // 更新维修单的处理人和状态
        repairDAO.updateHandlerAndStatus(repairId, handlerId, "处理中");
    }

    /**
     * 将维修单标记为已完成
     * @param repairId 维修单 ID
     */
    public void updateToCompleted(int repairId) {
        // 参数校验
        if (repairId <= 0) throw new IllegalArgumentException("repairId 不合法");
        // 检查维修单是否存在
        if (repairDAO.selectById(repairId) == null) {
            throw new RuntimeException("维修单不存在");
        }
        // 更新状态为已完成
        repairDAO.updateStatus(repairId, "已完成");
    }
}
