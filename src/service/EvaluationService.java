package src.service;

import src.dao.EvaluationDAO;
import src.dao.RepairDAO;
import src.entity.Evaluation;

/**
 * EvaluationService：评价业务层
 * 负责添加评价并可能更新相关维修单的状态或聚合统计
 */
public class EvaluationService {
    private EvaluationDAO evaluationDAO = new EvaluationDAO();
    private RepairDAO repairDAO = new RepairDAO();

    /**
     * 添加一条评价；通常需要验证维修单归属并保存评价，可能还会更新维修单的评价状态
     * @param evaluation 评价对象
     * @param submitterId 提交评价的学生学号（用于校验）
     */
    public void addEvaluation(Evaluation evaluation, String submitterId) {
        // TODO
        // 实现说明：
        // 1. 参数校验：检查 evaluation.getRepairID()、score 等是否有效
        // 2. 校验维修单归属：可以通过 repairDAO.selectById(repairId) 并检查 submitterID 是否与传入 submitterId 匹配（防止越权评价）
        // 3. 插入评价：调用 evaluationDAO.insertEvaluation(evaluation)
        // 4. 可选：更新 Repair 表的状态或标记该单已评价（例如 updateStatus 或新增字段）
        // 5. 异常处理与日志记录
    }
}
