package src.service;

import src.dao.StaffDAO;
import src.entity.Staff;

/**
 * AdminService：管理员业务层
 * 封装与管理员相关的业务逻辑（例如登录、统计等），由 View 层调用
 */
public class AdminService {
    private StaffDAO staffDAO = new StaffDAO();

    /**
     * 管理员登录
     * @param eid 工号
     * @param password 密码
     * @return Staff 登录成功返回 Staff 对象，否则返回 null
     */
    public Staff login(String eid, String password) {
        return staffDAO.selectByEidAndPwd(eid, password);
    }

    /**
     * 按学院统计维修数量（统计逻辑为 TODO，可能会调用 RepairDAO/StudentDAO）
     */
    public void statRepairByCollege() {
        // TODO
        // 实现说明：
        // 1. 汇总思路：通过 Repair 表关联 Student 表，按 Student.College 分组统计 Repair 数量
        // 2. SQL 示例：SELECT s.College, COUNT(r.RepairID) FROM Repair r JOIN Student s ON r.SubmitterID = s.SID GROUP BY s.College
        // 3. 执行查询并将结果格式化输出或返回数据结构供前端显示
        // 4. 处理缺失学号或空值的情况
    }

    /**
     * 统计维修评价（示例：按评分分布统计）
     */
    public void statRepairEvaluation() {
        // TODO
        // 实现说明：
        // 1. 可按评分统计：SELECT Score, COUNT(*) FROM Evaluation GROUP BY Score
        // 2. 也可结合 Repair 的时间或学院进行细分统计
        // 3. 执行查询并格式化结果供展示
    }
}
