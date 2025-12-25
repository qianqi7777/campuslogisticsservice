package src.view;

import java.util.Scanner;
import src.service.UserService;
import src.entity.Student;
import src.entity.Staff;

/**
 * 登录视图：命令行交互的登录界面
 * 提供学生、职工与管理员三种身份的登录入口
 */
public class LoginView {
    private Scanner scanner = new Scanner(System.in);
    private UserService userService = new UserService();

    /**
     * 显示登录主界面并处理用户选择
     */
    public void show() {
        while (true) {
            // 打印登录菜单
            System.out.println("\n=== 登录界面 ===");
            System.out.println("1. 学生登录");
            System.out.println("2. 职工登录");
            System.out.println("3. 管理员登录");
            System.out.println("0. 退出系统");
            System.out.print("请选择：");

            // 读取用户选择
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    loginStudent();
                    break;
                case "2":
                    loginStaff();
                    break;
                case "3":
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
     * 学生登录流程
     */
    private void loginStudent() {
        System.out.print("请输入学号：");
        String sid = scanner.nextLine();
        System.out.print("请输入密码：");
        String pwd = scanner.nextLine();

        Student student = userService.loginStudent(sid, pwd);
        if (student != null) {
            System.out.println("登录成功！欢迎 " + student.getSName());
            new StudentView(student).show();
        } else {
            System.out.println("登录失败，用户名或密码错误。");
        }
    }

    /**
     * 职工登录流程
     */
    private void loginStaff() {
        System.out.print("请输入工号：");
        String eid = scanner.nextLine();
        System.out.print("请输入密码：");
        String pwd = scanner.nextLine();

        Staff staff = userService.loginStaff(eid, pwd);
        if (staff != null) {
            // 检查是否是管理员 (这里简单通过职位判断，或者在 Staff 实体加字段)
            // 假设管理员也是 Staff，只是职位不同，或者 AdminView 也是一种 StaffView
            // 但根据需求，管理员有单独的 AdminView
            // 这里假设职位包含 "管理员" 字样则是管理员，否则是普通职工
            // 或者根据登录入口区分，这里是职工登录入口
            System.out.println("登录成功！欢迎 " + staff.getEName());
            new StaffView(staff).show();
        } else {
            System.out.println("登录失败，用户名或密码错误。");
        }
    }

    /**
     * 管理员登录流程
     */
    private void loginAdmin() {
        System.out.print("请输入管理员账号：");
        String eid = scanner.nextLine();
        System.out.print("请输入密码：");
        String pwd = scanner.nextLine();

        Staff admin = userService.loginStaff(eid, pwd);
        if (admin != null) {
            // 可以在这里校验职位是否为管理员
            System.out.println("管理员登录成功！欢迎 " + admin.getEName());
            new AdminView(admin).show();
        } else {
            System.out.println("登录失败，用户名或密码错误。");
        }
    }
}
