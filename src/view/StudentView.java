package src.view;

import src.entity.Student;
import java.util.Scanner;

/**
 * 学生控制台视图：StudentView
 * 提供学生用户可以执行的操作菜单（当前为占位示例）
 */
public class StudentView {
    private Student student;
    private Scanner scanner = new Scanner(System.in);

    public StudentView(Student student) {
        this.student = student;
    }

    /**
     * 显示学生主菜单并响应输入（实际功能由 Service 层实现并在此调用）
     */
    public void show() {
        while (true) {
            // 打印学生菜单
            System.out.println("\n=== 学生主菜单 (" + student.getSName() + ") ===");
            System.out.println("1. 报修服务 (提交/评价)");
            System.out.println("2. 预约服务 (查询/申请)");
            System.out.println("3. 校园卡服务 (查询/充值/挂失)");
            System.out.println("0. 注销登录");
            System.out.print("请选择：");

            // 读取用户输入
            String choice = scanner.nextLine();
            if ("0".equals(choice)) return;

            switch (choice) {
                case "1":
                    showRepairMenu();
                    break;
                case "2":
                    showReservationMenu();
                    break;
                case "3":
                    showCardMenu();
                    break;
                default:
                    System.out.println("无效选项");
            }
        }
    }

    private void showRepairMenu() {
        System.out.println("\n--- 报修服务 ---");
        System.out.println("1. 提交报修单");
        System.out.println("2. 评价维修");
        System.out.println("0. 返回");
        // TODO: 读取输入并调用 RepairService
    }

    private void showReservationMenu() {
        System.out.println("\n--- 预约服务 ---");
        System.out.println("1. 查询场地状态");
        System.out.println("2. 提交预约申请");
        System.out.println("0. 返回");
        // TODO: 读取输入并调用 ReservationService
    }

    private void showCardMenu() {
        System.out.println("\n--- 校园卡服务 ---");
        System.out.println("1. 查询余额");
        System.out.println("2. 充值");
        System.out.println("3. 挂失");
        System.out.println("0. 返回");
        // TODO: 读取输入并调用 CampusCardService
    }
}
