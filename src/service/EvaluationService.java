package src.service;

import src.dao.EvaluationDAO;
import src.dao.RepairDAO;
import src.entity.Evaluation;
import src.entity.Repair;

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
        // 基础参数校验
        if (evaluation == null || evaluation.getRepairID() <= 0) {
            throw new IllegalArgumentException("评价信息不完整");
        }
        if (evaluation.getScore() < 0) {
            throw new IllegalArgumentException("评分不合法");
        }
        // 校验维修单归属（可选）
        Repair r = repairDAO.selectById(evaluation.getRepairID());
        if (r == null) {
            throw new RuntimeException("对应的维修单不存在");
        }
        // 如需校验 submitterId 与维修单的 submitter 匹配，可启用以下校验
        if (submitterId != null && r.getSubmitterID() != null && !submitterId.equals(r.getSubmitterID())) {
            throw new RuntimeException("无权对该维修单进行评价");
        }

        // 插入评价记录
        evaluationDAO.insertEvaluation(evaluation);
    }

    /**
     * 根据维修单ID获取评价
     * @param repairId 维修单ID
     * @return Evaluation 对象
     */
    public Evaluation getEvaluationByRepairId(int repairId) {
        // 调用 DAO 查询
        return evaluationDAO.selectByRepairId(repairId);
    }
}
