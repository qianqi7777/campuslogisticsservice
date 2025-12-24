package src.view;

import java.util.Scanner;
import src.service.StudentService;
import src.service.AdminService;
import src.entity.Student;
import src.entity.Staff;

public class LoginView {
    private Scanner scanner = new Scanner(System.in);
    private StudentService studentService = new StudentService();
    private AdminService adminService = new AdminService();

    public void show() {
        while (true) {
            System.out.println("\n=== 登录界面 ===");
            System.out.println("1. 学生登录");
            System.out.println("2. 管理员登录");
            System.out.println("0. 退出系统");
            System.out.print("请选择：");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    loginStudent();
                    break;
                case "2":
                    loginAdmin();
                    break;
                case "0":
                    System.out.println("系统已退出。");
                    System.exit(0);
                default:
                    System.out.println("输入无效，请重试。");
            }
        }
    }

    private void loginStudent() {
        System.out.print("请输入学号：");
        String sid = scanner.nextLine();
        System.out.print("请输入密码：");
        String pwd = scanner.nextLine();

        Student student = studentService.login(sid, pwd);
        if (student != null) {
            System.out.println("登录成功！欢迎 " + student.getSName());
            new StudentView(student).show();
        } else {
            System.out.println("登录失败：学号不存在或密码错误。");
        }
    }

    private void loginAdmin() {
        System.out.print("请输入工号：");
        String eid = scanner.nextLine();
        System.out.print("请输入密码：");
        String pwd = scanner.nextLine();

        Staff staff = adminService.login(eid, pwd);
        if (staff != null) {
            System.out.println("登录成功！欢迎 " + staff.getEName());
            new AdminView(staff).show();
        } else {
            System.out.println("登录失败：工号不存在或密码错误。");
        }
    }
}
