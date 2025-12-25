package src.service;

import src.dao.RepairDAO;
import src.entity.Repair;
import java.util.List;

/**
 * 报修服务类
 * 处理报修申请、分配、状态更新、评价等
 */
public class RepairService {
    private RepairDAO repairDAO = new RepairDAO();

    // --- 学生端功能 ---

    /**
     * 提交报修单
     * @param repair 报修信息
     */
    public void submitRepair(Repair repair) {
        // TODO: 调用 DAO 插入报修单
    }

    /**
     * 评价维修 (学生)
     * @param repairId 维修单ID
     * @param comment 评价内容
     * @param score 评分
     */
    public void evaluateRepair(int repairId, String comment, int score) {
        // TODO: 调用 EvaluationDAO 插入评价
    }

    // --- 职工端功能 ---

    /**
     * 获取待处理/处理中的报修单 (职工)
     * @param staffId 职工ID
     * @return 报修单列表
     */
    public List<Repair> getAssignedRepairs(String staffId) {
        // TODO: 调用 DAO 查询
        return null;
    }

    /**
     * 更新维修状态 (职工)
     * @param repairId 维修单ID
     * @param status 新状态
     */
    public void updateRepairStatus(int repairId, String status) {
        // TODO: 调用 DAO 更新状态
    }

    /**
     * 查看评价 (职工)
     * @param staffId 职工ID
     */
    public void viewEvaluations(String staffId) {
        // TODO: 查询该职工相关维修单的评价
    }

    // --- 管理员功能 ---

    /**
     * 统计报修数量 (管理员)
     */
    public void getRepairStatistics() {
        // TODO: 统计各类报修数量
    }

    /**
     * 分配维修任务 (管理员)
     * @param repairId 维修单ID
     * @param staffId 职工ID
     */
    public void assignRepairTask(int repairId, String staffId) {
        // TODO: 更新维修单的 HandlerID
    }
}

