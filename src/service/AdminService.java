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
    }

    /**
     * 统计维修评价（示例：按评分分布统计）
     */
    public void statRepairEvaluation() {
        // TODO
    }
}
