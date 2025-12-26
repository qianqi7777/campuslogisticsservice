package src.service;

import src.dao.RepairDAO;
import src.dao.EvaluationDAO;
import src.entity.Repair;
import src.entity.Evaluation;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

/**
 * 报修服务类
 * 处理报修申请、分配、状态更新、评价等
 */
public class RepairService {
    private RepairDAO repairDAO = new RepairDAO();
    private EvaluationDAO evaluationDAO = new EvaluationDAO();

    // --- 学生端功能 ---

    /**
     * 提交报修单
     * @param repair 报修信息
     * @return 维修单ID
     */
    public int submitRepair(Repair repair) {
        return repairDAO.insertRepair(repair);
    }

    /**
     * 评价维修 (学生)
     * @param repairId 维修单ID
     * @param comment 评价内容
     * @param score 评分
     */
    public void evaluateRepair(int repairId, String comment, int score) {
        Evaluation eval = new Evaluation();
        eval.setRepairID(repairId);
        eval.setComment(comment);
        eval.setScore(score);
        evaluationDAO.insertEvaluation(eval);
    }

    /**
     * 获取学生提交的维修单 (学生)
     * @param studentId 学生学号
     * @return 维修单列表
     */
    public List<Repair> getRepairsByStudent(String studentId) {
        return repairDAO.selectBySubmitter(studentId);
    }

    // --- 职工端功能 ---

    /**
     * 获取待处理/处理中的报修单 (职工)
     * @param staffId 职工ID
     * @return 报修单列表
     */
    public List<Repair> getAssignedRepairs(String staffId) {
        return repairDAO.selectByHandler(staffId);
    }

    /**
     * 更新维修状态 (职工)
     * @param repairId 维修单ID
     * @param status 新状态
     */
    public void updateRepairStatus(int repairId, String status) {
        repairDAO.updateStatus(repairId, status);
    }

    /**
     * 查看评价 (职工)
     * @param staffId 职工ID
     */
    public void viewEvaluations(String staffId) {
        List<Repair> repairs = repairDAO.selectByHandler(staffId);
        System.out.println("--- 我的维修评价 ---");
        for (Repair r : repairs) {
            Evaluation e = evaluationDAO.selectByRepairId(r.getRepairID());
            if (e != null) {
                System.out.printf("维修单ID: %d, 评分: %d, 评价: %s\n", r.getRepairID(), e.getScore(), e.getComment());
            }
        }
    }

    /**
     * 获取维修评价 (职工/管理员)
     * @param repairId 维修单ID
     * @return 评价内容
     */
    public Evaluation getEvaluation(int repairId) {
        return evaluationDAO.selectByRepairId(repairId);
    }

    // --- 管理员功能 ---

    /**
     * 统计报修数量 (管理员)
     */
    public void getRepairStatistics() {
        List<Repair> all = repairDAO.selectAll();
        Map<String, Integer> stats = new HashMap<>();
        for (Repair r : all) {
            stats.put(r.getStatus(), stats.getOrDefault(r.getStatus(), 0) + 1);
        }
        System.out.println("--- 报修统计 ---");
        for (Map.Entry<String, Integer> entry : stats.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    /**
     * 分配维修任务 (管理员)
     * @param repairId 维修单ID
     * @param staffId 职工ID
     */
    public void assignRepairTask(int repairId, String staffId) {
        repairDAO.updateHandler(repairId, staffId);
        // 通常分配后状态变为 "处理中"
        repairDAO.updateStatus(repairId, "处理中");
    }

    /**
     * 分配维修任务 (管理员)
     * @param repairId 维修单ID
     * @param handlerId 处理人工号
     */
    public void assignHandler(int repairId, String handlerId) {
        repairDAO.updateHandler(repairId, handlerId);
    }

    /**
     * 获取所有维修单 (管理员)
     * @return 维修单列表
     */
    public List<Repair> getAllRepairs() {
        return repairDAO.selectAll();
    }
}
