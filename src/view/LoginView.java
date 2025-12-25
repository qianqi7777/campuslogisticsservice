package src.view;

import java.util.Scanner;
import src.service.StudentService;
import src.service.AdminService;
import src.entity.Student;
import src.entity.Staff;

/**
 * 登录视图：命令行交互的登录界面
 * 提供学生与管理员两种身份的登录入口，登录成功后跳转到对应视图
 */
public class LoginView {
    private Scanner scanner = new Scanner(System.in);
    private StudentService studentService = new StudentService();
    private AdminService adminService = new AdminService();

    /**
     * 显示登录主界面并处理用户选择
     */
    public void show() {
        while (true) {
            // 打印登录菜单
            System.out.println("\n=== 登录界面 ===");
            System.out.println("1. 学生登录");
            System.out.println("2. 管理员登录");
            System.out.println("0. 退出系统");
            System.out.print("请 选择：");

            // 读取用户选择
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

    /**
     * 学生登录流程：读取学号与密码并调用 StudentService 验证
     */
    private void loginStudent() {
        System.out.print("请输入学号：");
        String sid = scanner.nextLine();
        System.out.print("请输入密码：");
        String pwd = scanner.nextLine();

        // 调用 Service 进行登录验证
        Student student = studentService.login(sid, pwd);
        if (student != null) {
            System.out.println("登录成功！欢迎 " + student.getSName());
            // 跳转到学生视图
            new StudentView(student).show();
        } else {
            System.out.println("登录失败：学号不存在或密码错误。");
        }
    }

    /**
     * 管理员登录流程：读取工号与密码并调用 AdminService 验证
     */
    private void loginAdmin() {
        System.out.print("请输入工号：");
        String eid = scanner.nextLine();
        System.out.print("请输入密码：");
        String pwd = scanner.nextLine();

        // 调用 Service 进行登录验证
        Staff staff = adminService.login(eid, pwd);
        if (staff != null) {
            System.out.println("登录成功！欢迎 " + staff.getEName());
            // 跳转到管理员视图
            new AdminView(staff).show();
        } else {
            System.out.println("登录失败：工号不存在或密码错误。");
        }
    }
}
