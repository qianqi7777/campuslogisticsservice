package src.service;

import src.entity.Student;
import src.entity.Staff;
import src.dao.StudentDAO;
import src.dao.StaffDAO;

/**
 * 用户服务类
 * 处理登录、用户管理（管理员功能）
 */
public class UserService {
    private StudentDAO studentDAO = new StudentDAO();
    private StaffDAO staffDAO = new StaffDAO();

    /**
     * 学生登录
     * @param sid 学号
     * @param password 密码
     * @return 登录成功的 Student 对象，失败返回 null
     */
    public Student loginStudent(String sid, String password) {
        // TODO: 调用 StudentDAO 验证
        return null;
    }

    /**
     * 职工/管理员登录
     * @param eid 工号
     * @param password 密码
     * @return 登录成功的 Staff 对象，失败返回 null
     */
    public Staff loginStaff(String eid, String password) {
        // TODO: 调用 StaffDAO 验证
        return null;
    }

    /**
     * 添加用户 (管理员功能)
     * @param user 用户对象 (Student 或 Staff)
     */
    public void addUser(Object user) {
        // TODO: 判断类型并调用对应 DAO 添加
    }

    /**
     * 注销用户 (管理员功能)
     * @param userId 用户ID
     * @param type 用户类型
     */
    public void deleteUser(String userId, String type) {
        // TODO: 调用对应 DAO 删除
    }
}

