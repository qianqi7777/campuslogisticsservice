package src.service;

import src.dao.StudentDAO;
import src.entity.Student;

/**
 * StudentService：学生业务层
 * 封装学生相关的业务操作（目前仅有登录）
 */
public class StudentService {
    private StudentDAO studentDAO = new StudentDAO();

    /**
     * 学生登录
     * @param sid 学号
     * @param password 密码
     * @return Student 成功返回 Student 对象，否则返回 null
     */
    public Student login(String sid, String password) {
        return studentDAO.selectBySidAndPwd(sid, password);
    }
}
