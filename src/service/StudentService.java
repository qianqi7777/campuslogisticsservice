package src.service;

import src.dao.StudentDAO;
import src.entity.Student;

public class StudentService {
    private StudentDAO studentDAO = new StudentDAO();

    public Student login(String sid, String password) {
        return studentDAO.selectBySidAndPwd(sid, password);
    }
}
