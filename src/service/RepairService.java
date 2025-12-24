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
        // 实现说明：
        // 1. 参数校验：检查 content, submitterID 等必填字段
        // 2. 设置默认状态（如 "待处理"）并调用 repairDAO.insertRepair(repair)
        // 3. 可在插入成功后通知管理员或写入日志
        // 4. 处理异常并在需要时回滚事务（若有多表操作）
    }

    /**
     * 分配维修任务给指定教职工
     * @param repairId 维修单 ID
     * @param handlerId 处理人工号
     */
    public void assignRepairTask(int repairId, String handlerId) {
        // TODO
        // 实现说明：
        // 1. 参数校验：检查 repairId 是否存在，handlerId 对应的教职工是否存在（调用 staffDAO.selectByEid）
        // 2. 检查该维修单当前状态是否允许分配（例如不在已完成状态）
        // 3. 调用 repairDAO.updateHandlerAndStatus(repairId, handlerId, "处理中")
        // 4. 可发送通知给处理人（可选）
        // 5. 处理异常并记录操作日志
    }

    /**
     * 将维修单标记为已完成
     * @param repairId 维修单 ID
     */
    public void updateToCompleted(int repairId) {
        // TODO
        // 实现说明：
        // 1. 参数校验：检查 repairId 是否存在
        // 2. 调用 repairDAO.updateStatus(repairId, "已完成")
        // 3. 可在完成后触发评价流程（例如通知学生进行评价）
        // 4. 处理异常并记录日志
    }
}
