package src.service;

import java.util.List;
import src.dao.StaffDAO;
import src.dao.StudentDAO;
import src.entity.Staff;
import src.entity.Student;

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
        return studentDAO.selectBySidAndPwd(sid, password);
    }

    /**
     * 职工/管理员登录
     * @param eid 工号
     * @param password 密码
     * @return 登录成功的 Staff 对象，失败返回 null
     */
    public Staff loginStaff(String eid, String password) {
        return staffDAO.selectByEidAndPwd(eid, password);
    }

    /**
     * 添加用户 (管理员功能)
     * @param user 用户对象 (Student 或 Staff)
     * @return 是否添加成功
     */
    public boolean addUser(Object user) {
        if (user instanceof Student) {
            return studentDAO.insertStudent((Student) user);
        } else if (user instanceof Staff) {
            return staffDAO.insertStaff((Staff) user);
        }
        return false;
    }

    /**
     * 注销用户 (管理员功能)
     * @param userId 用户ID
     * @param type 用户类型 ("student" 或 "staff")
     * @return 是否删除成功
     */
    public boolean deleteUser(String userId, String type) {
        if ("student".equalsIgnoreCase(type)) {
            return studentDAO.deleteStudent(userId);
        } else if ("staff".equalsIgnoreCase(type)) {
            return staffDAO.deleteStaff(userId);
        }
        return false;
    }

    /**
     * 获取所有学生列表 (管理员功能)
     * @return 学生列表
     */
    public List<Student> getAllStudents() {
        return studentDAO.selectAll();
    }

    /**
     * 获取所有职工列表 (管理员功能)
     * @return 职工列表
     */
    public List<Staff> getAllStaff() {
        return staffDAO.selectAll();
    }
}
